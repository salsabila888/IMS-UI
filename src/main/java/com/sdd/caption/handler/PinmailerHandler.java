package com.sdd.caption.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.util.media.Media;

import com.sdd.caption.dao.MmenuDAO;
import com.sdd.caption.dao.TcounterengineDAO;
import com.sdd.caption.dao.TorderDAO;
import com.sdd.caption.dao.TpaketDAO;
import com.sdd.caption.dao.TpaketdataDAO;
import com.sdd.caption.dao.TpinmailerbranchDAO;
import com.sdd.caption.dao.TpinmailerdataDAO;
import com.sdd.caption.dao.TpinmailerfileDAO;
import com.sdd.caption.domain.Mbranch;
import com.sdd.caption.domain.Mmenu;
import com.sdd.caption.domain.Mproduct;
import com.sdd.caption.domain.Muser;
import com.sdd.caption.domain.Torder;
import com.sdd.caption.domain.Tpaket;
import com.sdd.caption.domain.Tpaketdata;
import com.sdd.caption.domain.Tpinmailerbranch;
import com.sdd.caption.domain.Tpinmailerdata;
import com.sdd.caption.domain.Tpinmailerfile;
import com.sdd.caption.utils.AppData;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class PinmailerHandler {

	public static Map<String, Object> doInsert(Tpinmailerfile objForm, Media media, Muser oUser) throws Exception {
		Map<String, Object> objResult = new HashMap<>();
		String error = "";
		TpinmailerfileDAO oDao = new TpinmailerfileDAO();
		TpinmailerbranchDAO branchDao = new TpinmailerbranchDAO();
		TpinmailerdataDAO dataDao = new TpinmailerdataDAO();
		DateFormat datedbFormatter = new SimpleDateFormat("yyMMdd");
		Map<String, Mbranch> mapBranch = new HashMap<String, Mbranch>();
		Map<String, Mproduct> mapProduct = new HashMap<String, Mproduct>();
		Map<String, Tpinmailerbranch> mapTbranch;
		Map<String, List<Tpinmailerdata>> mapTdata;
		List<Tpinmailerdata> listTod = new ArrayList<>();
		Session session = StoreHibernateUtil.openSession();
		;
		Transaction transaction = null;
		boolean isValid = false;
		Integer totaldata = 0;
		Integer totalinserted = 0;
		try {
			transaction = session.beginTransaction();
			objForm.setTpinmailerfilepk(null);
			objForm.setBatchid(new TcounterengineDAO().generateCounter(AppUtils.CE_ORDER));
			objForm.setFilename(media.getName());
			objForm.setUploadedby(oUser.getUserid());
			objForm.setStatus(AppUtils.STATUS_ORDER_WAITAPPROVAL);

			oDao.save(session, objForm);
			transaction.commit();
			isValid = true;
		} catch (Exception e) {
			transaction.rollback();
			isValid = false;
			e.printStackTrace();
			error = e.getMessage();
		} finally {
			session.close();
		}

		if (isValid) {
			BufferedReader reader = null;
			try {
				for (Mbranch obj : AppData.getMbranch()) {
					mapBranch.put(obj.getBranchid(), obj);
				}
				mapProduct = new HashMap<String, Mproduct>();
				for (Mproduct obj : AppData.getMproduct("productgroup = '" + AppUtils.PRODUCTGROUP_CARD + "'")) {
					mapProduct.put(obj.getProductcode() + obj.getIsinstant(), obj);
				}

				if (media.isBinary()) {
					reader = new BufferedReader(new InputStreamReader(media.getStreamData()));
				} else {
					reader = new BufferedReader(media.getReaderData());
				}

				String line = "";
				String cardno = "";
				int linecontent = 0;
				int totaldatabranch = 0;
				String branchid = "";
				boolean isContent = false;
				Tpinmailerbranch tbranch = null;
				mapTbranch = new HashMap<>();
				mapTdata = new HashMap<>();
				while ((line = reader.readLine()) != null) {
					try {
						if (line.trim().equals("CARD  PRODUCTION  CONTROL  REPORT")) {
							isContent = true;
							linecontent = 1;
						}

						if (isContent) {
							if (linecontent == 3 && !line.substring(5, 8).equals(branchid)) {
								if (tbranch != null) {
									Tpinmailerbranch objtmp = mapTbranch.get(branchid);
									if (objtmp != null) {
										totaldatabranch += objtmp.getTotaldata();
									}
									tbranch.setTotaldata(totaldatabranch);

									mapTbranch.put(branchid, tbranch);

									List<Tpinmailerdata> listtmp = mapTdata.get(branchid);
									if (listtmp != null) {
										listTod.addAll(listtmp);
									}

									mapTdata.put(branchid, listTod);
								}

								branchid = line.substring(5, 8);

								tbranch = new Tpinmailerbranch();
								tbranch.setTpinmailerfile(objForm);
								tbranch.setBranchid(branchid);
								tbranch.setStatus(AppUtils.STATUS_ORDER_WAITAPPROVAL);
								Mbranch mbranch = mapBranch.get(branchid);
								if (mbranch != null) {
									tbranch.setMbranch(mbranch);
								}
								totaldatabranch = 0;

								listTod = new ArrayList<>();
							}
							if (linecontent > 7) {
								cardno = line.substring(9, 28).trim();
								if (cardno.length() >= 16 && !cardno.contains("*")) {
									Tpinmailerdata data = new Tpinmailerdata();
									data.setCardno(cardno);
									data.setName(line.substring(41, 74));
									data.setOrderdate(datedbFormatter.parse(line.substring(111, 117)));
									data.setProductcode(line.substring(127).trim());
									data.setBranchid(branchid);
									data.setKlncode(line.substring(29, 31));
									data.setSeqno(line.substring(0, 6));
									data.setIswithcard(line.substring(37, 38));

									if (data.getName().trim().equals(""))
										data.setIsinstant("Y");
									else
										data.setIsinstant("N");

									if (data.getProductcode() != null) {
										Mproduct mproduct = mapProduct.get(data.getProductcode() + data.getIsinstant());
										if (mproduct != null) {
											data.setMproduct(mproduct);
										}
									}

									listTod.add(data);

									tbranch.setOrderdate(data.getOrderdate());

									totaldata++;
									totaldatabranch++;
								} else {
									isContent = false;
								}
							}
							linecontent++;
						}

					} catch (Exception e) {
						e.printStackTrace();
						if (error.length() > 0)
							error += ". \n";
						error += e.getMessage();
						System.out.println(line);
					}
				}

				if (tbranch != null) {
					Tpinmailerbranch objtmp = mapTbranch.get(branchid);
					if (objtmp != null) {
						totaldatabranch += objtmp.getTotaldata();
					}
					tbranch.setTotaldata(totaldatabranch);

					mapTbranch.put(branchid, tbranch);

					List<Tpinmailerdata> listtmp = mapTdata.get(branchid);
					if (listtmp.size() > 0) {
						listTod.addAll(listtmp);
					}

					mapTdata.put(branchid, listTod);
				}

				session = StoreHibernateUtil.openSession();
				transaction = session.beginTransaction();
				try {
					for (Entry<String, Tpinmailerbranch> entry : mapTbranch.entrySet()) {
						branchDao.save(session, entry.getValue());

						List<Tpinmailerdata> listTdata = mapTdata.get(entry.getValue().getBranchid());
						for (Tpinmailerdata data : listTdata) {
							data.setTpinmailerbranch(entry.getValue());
							dataDao.save(session, data);
							totalinserted++;
						}
					}

					objForm.setTotaldata(totalinserted);
					oDao.save(session, objForm);

					transaction.commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (error.length() > 0)
						error += ". \n";
					error += e.getMessage();
				} finally {
					session.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (error.length() > 0)
					error += ". \n";
				error += e.getMessage();
			}
		}
		if (error.length() > 0)
			objResult.put("error", error);
		objResult.put("totaldata", totaldata);
		objResult.put("totalinserted", totalinserted);
		return objResult;
	}

	public static Map<String, Object> doDone(Map<Integer, Torder> mapData, Muser oUser) throws Exception {
		Map<String, Object> objResult = new HashMap<>();
		String error = "";
		TpinmailerfileDAO oDao = new TpinmailerfileDAO();
		TorderDAO torderDao = new TorderDAO();
		TpaketDAO tpaketDao = new TpaketDAO();
		TpaketdataDAO tpaketdataDao = new TpaketdataDAO();
		Session session = null;
		Transaction transaction = null;
		int failed = 0;
		for (Entry<Integer, Torder> entry : mapData.entrySet()) {
			session = StoreHibernateUtil.openSession();
			transaction = session.beginTransaction();
			try {
				Torder obj = entry.getValue();
				if (obj.getStatus().equals(AppUtils.STATUS_ORDER_PRODUKSI)) {
					obj.setStatus(AppUtils.STATUS_DELIVERY_DELIVERYORDER);
					obj.setDlvstarttime(new Date());
					torderDao.save(session, obj);

					obj.getTpinmailerfile().setStatus(AppUtils.STATUS_DELIVERY_DELIVERYORDER);
					oDao.save(session, obj.getTpinmailerfile());

					Tpaket tpaket = new Tpaket();
					tpaket.setTorder(obj);
					tpaket.setMproduct(obj.getMproduct());
					tpaket.setPaketid(new TcounterengineDAO().generateYearMonthCounter(AppUtils.CE_PAKET));
					tpaket.setProductgroup(obj.getProductgroup());
					tpaket.setTotaldata(obj.getTotaldata());
					tpaket.setTotaldone(obj.getTotaldata());
					tpaket.setOrderdate(obj.getEntrytime());
					tpaket.setStatus(AppUtils.STATUS_DELIVERY_PAKETPROSES);
					tpaket.setProcessedby(oUser.getUserid());
					tpaket.setProcesstime(new Date());
					tpaketDao.save(session, tpaket);

					for (Tpinmailerbranch tbranch : new TpinmailerbranchDAO().listByFilter(
							"tpinmailerfile.tpinmailerfilepk = " + obj.getTpinmailerfile().getTpinmailerfilepk(),
							"tpinmailerbranchpk")) {
						if (tbranch.getMbranch() != null) {
							Tpaketdata tpaketdata = new Tpaketdata();
							tpaketdata.setTpaket(tpaket);
							tpaketdata.setNopaket(new TcounterengineDAO().generateNopaket());
							tpaketdata.setProductgroup(obj.getProductgroup());
							tpaketdata.setMbranch(tbranch.getMbranch());
							tpaketdata.setOrderdate(obj.getEntrytime());
							tpaketdata.setQuantity(tbranch.getTotaldata());
							tpaketdata.setStatus(AppUtils.STATUS_DELIVERY_PAKETPROSES);
							tpaketdata.setIsdlv("N");
							tpaketdata.setTpinmailerbranch(tbranch);
							tpaketdataDao.save(session, tpaketdata);
						}
					}
					
					Mmenu menu = new MmenuDAO().findByFilter(
							"menupath = '/view/delivery/deliveryjob.zul'");
					NotifHandler.doNotif(session, menu, "Paket Pin Mailer yang harus diOrder Delivery");
					
					FlowHandler.doFlow(session, null, obj, AppUtils.PROSES_OUTGOING,
							obj.getMemo(), oUser.getUserid());

					transaction.commit();
				} else {
					failed++;
				}
			} catch (HibernateException e) {
				e.printStackTrace();
				if (error.length() > 0)
					error += ". \n";
				error += e.getMessage();
			} catch (Exception e) {
				e.printStackTrace();
				if (error.length() > 0)
					error += ". \n";
				error += e.getMessage();
			} finally {
				session.close();
			}
		} 
		objResult.put("failed", failed);
		if (error.length() > 0)
			objResult.put("error", error);
		return objResult;
	}
}
