package com.sdd.caption.services;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.pojo.FmtReqAll;

public class ConnectPOS {

	private static Tdelivery courier;
	
	public ConnectPOS() {

	}
	
	public static void main(int[] angka) {
		System.out.println("hello test");
	}
	
	public static void main(String ... test) {
		System.out.println("hello test");
	}

	/*public static void main(String[] args) {
		try {
			RequestPOS.getToken(courier);

			// REQ FEE
			
			 * FmtReqFee reqFee = new FmtReqFee(); reqFee.setCustomerid("");
			 * reqFee.setDesttypeid("1"); reqFee.setItemtypeid("1");
			 * reqFee.setShipperzipcode("55111"); reqFee.setReceiverzipcode("55121");
			 * reqFee.setWeight(140); reqFee.setLength(0); reqFee.setWidth(0);
			 * reqFee.setHeight(0); reqFee.setDiameter(0); reqFee.setValuegoods(0);
			 * 
			 * String rsp = RequestPOS.getFee(reqFee); JSONObject obj = new JSONObject(rsp);
			 * System.out.println("OBJ : " + obj.get("response")); JSONObject jArr = new
			 * JSONObject("data"); System.out.println("dataArr : " + jArr);
			 
			// REQ TRACK DETAIL
			FmtReqAll reqAll = new FmtReqAll();
			reqAll.setBarcode("17894155760");
			String rsp = RequestPOS.getDetail(reqAll, courier);

			JSONObject obj = new JSONObject(rsp);
			System.out.println("OBJ : " + obj);
			if (obj.isNull("response")) {
				System.out.println("code tidak ditemikan");
			} else {
				System.out.println("OBJ RESPONSE : " + obj.get("response"));  
				JSONObject response = obj.getJSONObject("response");
				System.out.println("OBJ DATA : " + response);
				JSONArray objArr = response.getJSONArray("data");
				System.out.println("SIZE : " + objArr.length());
				for (int i = 0; i < objArr.length(); i++) {
					System.out.println("KEY" + objArr.get(i));
					JSONObject objData = objArr.getJSONObject(i);
					int length = objData.get("description").toString().indexOf("~");
					System.out.println("description : " + objData.get("description").toString().substring(0, length));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
