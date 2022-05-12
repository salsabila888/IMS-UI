package com.sdd.caption.handler;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sdd.caption.dao.MpicproductDAO;
import com.sdd.caption.dao.MsysparamDAO;
import com.sdd.caption.dao.TbranchitemtrackDAO;
import com.sdd.caption.dao.TbranchstockDAO;
import com.sdd.caption.dao.TbranchstockitemDAO;
import com.sdd.caption.dao.TembossdataDAO;
import com.sdd.caption.dao.TfilebranchactvDAO;
import com.sdd.caption.domain.Mpicproduct;
import com.sdd.caption.domain.Msysparam;
import com.sdd.caption.domain.Tbranchitemtrack;
import com.sdd.caption.domain.Tbranchstock;
import com.sdd.caption.domain.Tbranchstockitem;
import com.sdd.caption.domain.Tembossdata;
import com.sdd.caption.domain.Tfilebranchactv;
import com.sdd.caption.utils.AppUtils;
import com.sdd.utils.db.StoreHibernateUtil;

public class ActivationDataHandler implements Job {

	private TbranchstockDAO tbranchstockDao = new TbranchstockDAO();
	private TembossdataDAO embossdataDAO = new TembossdataDAO();
	private TfilebranchactvDAO tfileDao = new TfilebranchactvDAO();

	private DateFormat datedbFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
	private DateFormat dateLocalFormatter = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap datamap = arg0.getJobDetail().getJobDataMap();
		String realpath = (String) datamap.get("realpath");

		FTPClient ftpClient = new FTPClient();
		try {
			String server = "";
			String port = "";
			String user = "";
			String pass = "";
			List<Msysparam> objParam = new MsysparamDAO()
					.listByFilter("paramgroup = '" + AppUtils.PARAM_GROUP_BRANCHACTIVATIONHOST + "'", "orderno");
			for (Msysparam obj : objParam) {
				if (obj.getParamcode().equals(AppUtils.PARAM_BRANCHACTIVATIONHOSTIP))
					server = obj.getParamvalue().trim();
				else if (obj.getParamcode().equals(AppUtils.PARAM_BRANCHACTIVATIONHOSTPORT))
					port = obj.getParamvalue().trim();
				else if (obj.getParamcode().equals(AppUtils.PARAM_BRANCHACTIVATIONHOSTUSERID))
					user = obj.getParamvalue().trim();
				else if (obj.getParamcode().equals(AppUtils.PARAM_BRANCHACTIVATIONHOSTPASSWORD))
					pass = obj.getParamvalue().trim();
			}

			Map<String, String> mapStatusdesc = new HashMap<String, String>();
			Map<String, String> mapStatus = new HashMap<String, String>();

			mapStatusdesc.put("Reissue stop", "Stop Penerbitan Ulang Kartu");
			mapStatusdesc.put("Reissue", "Penerbitan Ulang Kartu");
			mapStatusdesc.put("Returned", "Kartu Kembali ke Bank");
			mapStatusdesc.put("Hot", "Kartu diblokir permanen");
			mapStatusdesc.put("Closed", "Kartu ditutup");
			mapStatusdesc.put("Expired", "Masa berlaku kartu habis");
			mapStatusdesc.put("Normal", "Kartu aktif");
			mapStatusdesc.put("Warm", "Kartu diblokir");
			mapStatusdesc.put("Unknown", "Selain Status yang ada, dia masuknya Unknown");

			mapStatus.put("Reissue stop", "RS");
			mapStatus.put("Reissue", "RI");
			mapStatus.put("Returned", "RT");
			mapStatus.put("Hot", "HT");
			mapStatus.put("Closed", "CL");
			mapStatus.put("Expired", "EX");
			mapStatus.put("Normal", "NR");
			mapStatus.put("Warm", "WR");
			mapStatus.put("Unknown", "UN");

			// ftpClient.addProtocolCommandListener(new PrintCommandListener(new
			// PrintWriter(System.out), true));
			ftpClient.connect(server, Integer.parseInt(port));
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			// ftpClient.execPBSZ(0);
			// ftpClient.execPROT("P");

			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				System.out.println("Connect failed");
				return;
			}

			boolean success = ftpClient.login(user, pass);

			if (!success) {
				System.out.println("Could not login to the server");
				return;
			}

			List<String> objFiles = new ArrayList<>();
			String filepath = realpath + AppUtils.FILES_ROOT_PATH + AppUtils.ACTIVATION_PATH;
			FTPFile[] files = ftpClient.listFiles("/");
			if (files != null && files.length > 0) {
				for (FTPFile file : files) {
					String filename = file.getName();
					if (filename.endsWith(".txt")) {
						if (tfileDao.findById(filename) == null) {
							if (!objFiles.contains(filename)) {
								System.out.println(filename);

								OutputStream outputStream1 = new BufferedOutputStream(
										new FileOutputStream(filepath + "/" + filename));
								boolean isretrived = ftpClient.retrieveFile(filename, outputStream1);
								outputStream1.close();
								if (isretrived) {
									Session session = StoreHibernateUtil.openSession();
									Transaction transaction = session.beginTransaction();
									try {
										Tfilebranchactv obj = new Tfilebranchactv();
										obj.setFilename(filename);
										obj.setFilegettime(new Date());
										tfileDao.save(session, obj);
										transaction.commit();
										objFiles.add(filename);
									} catch (Exception e) {
										transaction.rollback();
										e.printStackTrace();
									} finally {
										session.close();
									}

									try {
										boolean deleted = ftpClient.deleteFile(filename);
										if (deleted) {
											System.out.println("The file was deleted successfully.");
										} else {
											System.out.println("Could not delete the file.");
										}
									} catch (IOException ex) {
										System.out.println("Oh no, there was an error: " + ex.getMessage());
									}
								}
							}
						}
					}

				}
			}

			if (objFiles.size() < 1) {
				// Kirim email ke PIC
				try {
					String[] recipients = null;
					List<Mpicproduct> listPic = new MpicproductDAO().listByFilter("0=0", "picname");
					if (listPic.size() > 0) {
						recipients = new String[listPic.size()];
						int idx = 0;
						for (Mpicproduct mpicproduct : listPic) {
							recipients[idx++] = mpicproduct.getPicemail();
						}
					}

					if (recipients != null) {
						String smtpname = "";
						String smtpport = "";
						String mailid = "";
						String mailpass = "";
						List<Msysparam> mailParam = new MsysparamDAO()
								.listByFilter("paramgroup = '" + AppUtils.PARAM_GROUP_MAIL + "'", "orderno");
						for (Msysparam obj : mailParam) {
							if (obj.getParamcode().equals(AppUtils.PARAM_SMTPNAME))
								smtpname = obj.getParamvalue();
							else if (obj.getParamcode().equals(AppUtils.PARAM_SMTPPORT))
								smtpport = obj.getParamvalue();
							else if (obj.getParamcode().equals(AppUtils.PARAM_MAILID))
								mailid = obj.getParamvalue();
							else if (obj.getParamcode().equals(AppUtils.PARAM_MAILPASSWORD))
								mailpass = obj.getParamvalue();
						}

						MailUtil mailUtils = new MailUtil();
						mailUtils.setSmtpname(smtpname);
						mailUtils.setSmtpport(Integer.parseInt(smtpport));
						mailUtils.setMailid(mailid);
						mailUtils.setMailpassword(mailpass);
						mailUtils.setFrom("Caption <" + mailid + ">");
						mailUtils.setSubject("Caption - Alert File Activation");
						mailUtils.setRecipients(recipients);

						StringBuffer bufferContent = new StringBuffer();
						bufferContent.append("<p>Kepada Yth rekan DMA Support,</p>");
						bufferContent.append("<br/>");
						bufferContent.append("<p>Mohon bantuannya untuk penurunan data aktivasi kartu (AktivasiKartu_"
								+ dateFormatter.format(new Date()) + ".txt) tanggal "
								+ dateLocalFormatter.format(new Date())
								+ " yang saat ini belum tersedia di ftp 192.168.98.78:21.</p>");
						bufferContent.append("</table>");
						bufferContent.append("<br/>");
						bufferContent.append("<p>Demikian disampaikan, Atas bantuannya diucapkan terima kasih.</p>");
						bufferContent.append("<br/>");
						bufferContent.append("<p>Regards,,</p>");
						bufferContent.append("<br/>");
						bufferContent.append("<p>Tim Produksi Kartu Debit</p>");
						bufferContent.append("<p>Gd. BNI BSD Lt. 13</p>");
						mailUtils.setBodymsg(bufferContent.toString());
						MailSender.sendSSLMessage(mailUtils);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}

			for (String filename : objFiles) {
				BufferedReader reader = new BufferedReader(new FileReader(filepath + "/" + filename));
				try {
					String line = "";
					while ((line = reader.readLine()) != null) {
						try {

							String[] arData = line.split("\\@\\~\\|");
							String cardno = arData[0].trim();
							String status = arData[13].trim();
							String dateactivated = arData[14].trim();

							List<Tembossdata> dataList = embossdataDAO.listByFilter("cardno = '" + cardno + "' and isactivated = 'N'",
									"tembossdatapk");
							Tembossdata tembossdata = null;
							if (dataList.size() > 0) {
								Session session = StoreHibernateUtil.openSession();
								Transaction transaction = session.beginTransaction();
								try {
									boolean isValid = false;
									for (Tembossdata data : dataList) {
										tembossdata = data;
										data.setIsactivated("Y");
										data.setDateactivated(datedbFormatter.parse(dateactivated));
										data.setActivatedtime(new Date());
										data.setFilebranchactv(filename);
										embossdataDAO.save(session, data);

										if (tembossdata.getTembossbranch().getStatus()
												.equals(AppUtils.STATUSBRANCH_PROSESDELIVERY)
												|| tembossdata.getTembossbranch().getStatus()
														.equals(AppUtils.STATUSBRANCH_DELIVERED))
											isValid = true;
									}

									if (tembossdata != null && isValid) {
										if (tembossdata.getMproduct() != null && tembossdata.getMbranch() != null) {
											Tbranchstock tbranchstock = tbranchstockDao.findByFilter(
													"mbranch.mbranchpk = " + tembossdata.getMbranch().getMbranchpk()
															+ " and mproduct.mproductpk = "
															+ tembossdata.getMproduct().getMproductpk()
															+ " and outlet = '" + tembossdata.getKlncode() + "'");
											if (tbranchstock != null) {
												tbranchstock.setStockactivated(tbranchstock.getStockactivated() + 1);
												tbranchstock.setStockcabang(tbranchstock.getStockdelivered()
														- tbranchstock.getStockactivated());
												tbranchstockDao.save(session, tbranchstock);

												Tbranchstockitem item = new TbranchstockitemDAO().findByFilter(
														"tbranchstockfk = " + tbranchstock.getTbranchstockpk());
												if (item != null) {
													item.setStatus(mapStatus.get(status));
													new TbranchstockitemDAO().save(session, item);
												}

												Tbranchitemtrack track = new Tbranchitemtrack();
												track.setMproduct(tembossdata.getMproduct());
												track.setItemno(cardno);
												track.setProductgroup(tembossdata.getMproduct().getProductgroup());
												track.setTrackstatus(mapStatus.get(status));
												track.setTracktime(new Date());
												if (mapStatusdesc.get(status) != null)
													track.setTrackdesc(mapStatusdesc.get(status));
												new TbranchitemtrackDAO().save(session, track);
											}
										}
									}
									transaction.commit();
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									session.close();
								}
							}
						} catch (HibernateException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (HibernateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				reader.close();
				// System.out.println(filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private Date getDateCustomFormat(Cell cell) throws Exception {
		Date date = null;
		DataFormatter poiFormatter = new DataFormatter();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CellStyle style = cell.getSheet().getWorkbook().createCellStyle();
		DataFormat format = cell.getSheet().getWorkbook().createDataFormat();
		style.setDataFormat(format.getFormat("[$-809]yyyy-MM-dd;@"));
		cell.setCellStyle(style);
		String sdate = poiFormatter.formatCellValue(cell);
		System.out.println("getDateCustomFormat sdate " + sdate);
		date = dateFormat.parse(sdate);
		return date;
	}

}
