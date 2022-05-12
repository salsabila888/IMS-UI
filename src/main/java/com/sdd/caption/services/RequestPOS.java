package com.sdd.caption.services;

import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdd.caption.dao.McouriervendorDAO;
import com.sdd.caption.domain.Mcouriervendor;
import com.sdd.caption.domain.Tdelivery;
import com.sdd.caption.pojo.FmtReqAll;
import com.sdd.caption.pojo.FmtReqFee;

import com.sdd.utils.SysUtils;
import com.sun.jersey.api.client.ClientHandlerException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestPOS {
	final static Logger logback = LoggerFactory.getLogger(RequestPOS.class);
	private static String host = "";
	private static String port = "";
	private static String path = "";
	private static String url = "";

	public RequestPOS() {

	}

	// UNTUK GET TOKEN FROM POS INDONESIA
	public static String getToken(Tdelivery obj) {
		// public static void main(String[] args) {
		String url = "";

		try {
			//courier = new McouriervendorDAO().findByPk(7);
			url = obj.getMcouriervendor().getUrltoken().trim();
			//url = SysUtils.URL_PORT + SysUtils.METHOD_TOKEN;
			System.out.println("URL : " + url);

			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
					.readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
			RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials");
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/x-www-form-urlencoded")
					.addHeader("Accept", "application/json")
					.addHeader("Authorization", "Basic " + obj.getMcouriervendor().getCostumerauth()).build();
			Response response = client.newCall(request).execute();

			System.out.println("Respon : " + response);
			String json = response.body().string();

			JSONObject jsonObj = new JSONObject(json);
			SysUtils.token = (String) jsonObj.get("access_token");
			System.out.println("Token : " + SysUtils.token);

		} catch (ClientHandlerException ce) {
			logback.error(ce.getMessage());
			ce.printStackTrace();
		} catch (Exception e) {
			logback.error(e.getMessage());
			e.printStackTrace();
		}

		return SysUtils.token;
	}

	public static String getFee(FmtReqFee reqFee) {
		// public static void main(String[] args) {
		String rsp = "";
		String url = "";

		try {

			url = SysUtils.URL_PORT_PATH + SysUtils.METHOD_GETFEE;
			System.out.println("URL : " + url);
			System.out.println("FmtReqFee : " + reqFee.toString());

			ObjectMapper mapper = new ObjectMapper();
			String jsonRqs = mapper.writeValueAsString(reqFee);

			rsp = callAPI(url, jsonRqs);
			System.out.println("rsp : " + rsp);

		} catch (ClientHandlerException ce) {
			logback.error(ce.getMessage());
			ce.printStackTrace();
		} catch (Exception e) {
			logback.error(e.getMessage());
			e.printStackTrace();
		}

		return rsp;
	}

	// CALL API getTrackAndTrace
	public static String getTrack(FmtReqAll reqAll) {
		// public static void main(String[] args) {
		String rsp = "";
		String url = "";

		try {
			
			url = SysUtils.URL_PORT_PATH + SysUtils.METHOD_GETTRACK;
			System.out.println("URL : " + url);

			ObjectMapper mapper = new ObjectMapper();
			String jsonRqs = mapper.writeValueAsString(reqAll);
			rsp = callAPI(url, jsonRqs);
			System.out.println("rsp : " + rsp);

		} catch (ClientHandlerException ce) {
			logback.error(ce.getMessage());
			ce.printStackTrace();
		} catch (Exception e) {
			logback.error(e.getMessage());
			e.printStackTrace();
		}

		return rsp;
	}

	// CALL API getTrackAndTraceDetail
	public static String getDetail(FmtReqAll reqAll, Tdelivery obj) {
		String rsp = "";
		String url = "";

		try {
			url = obj.getMcouriervendor().getUrltracking().trim();
			//url = SysUtils.URL_PORT_PATH + SysUtils.METHOD_GETDETAIL;
			System.out.println("URL : " + url);

			ObjectMapper mapper = new ObjectMapper();
			String jsonRqs = mapper.writeValueAsString(reqAll);
			rsp = callAPI(url, jsonRqs);
			System.out.println("rsp : " + rsp);

		} catch (ClientHandlerException ce) {
			logback.error(ce.getMessage());
			ce.printStackTrace();
		} catch (Exception e) {
			logback.error(e.getMessage());
			e.printStackTrace();
		}

		return rsp;
	}

	// CALL API getTrackAndTraceLastStatus
	public static String getStatus(FmtReqAll reqAll) {
		String rsp = "";
		String url = "";

		try {
			url = SysUtils.URL_PORT_PATH + SysUtils.METHOD_GETSTATUS;
			System.out.println("URL : " + url);

			rsp = callAPI(url, reqAll.toString());

		} catch (ClientHandlerException ce) {
			logback.error(ce.getMessage());
			ce.printStackTrace();
		} catch (Exception e) {
			logback.error(e.getMessage());
			e.printStackTrace();
		}

		return rsp;
	}

	// CALL API getTrackAndTraceLuarNegeri
	public static String getTrackLN(FmtReqAll reqAll) {
		String rsp = "";
		String url = "";

		try {
			url = SysUtils.URL_PORT_PATH + SysUtils.METHOD_GETTRACK_LN;
			System.out.println("URL : " + url);
			System.out.println("FmtReqAll" + reqAll.toString());

			rsp = callAPI(url, reqAll.toString());

		} catch (ClientHandlerException ce) {
			logback.error(ce.getMessage());
			ce.printStackTrace();
		} catch (Exception e) {
			logback.error(e.getMessage());
			e.printStackTrace();
		}

		return rsp;
	}

	// FUNCTION UNTUK CALL PROTOKOL HTTP CLIENT
	private static String callAPI(String url, String req) {
		String rsp = "";
		try {
			OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
					.readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, req);
			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").addHeader("Accept", "application/json")
					.addHeader("Authorization", "Bearer " + SysUtils.token).addHeader("X-POS-USER", "crossborder")
					.addHeader("X-POS-PASSWORD", "crossborder@123").build();
			Response response = client.newCall(request).execute();
			System.out.println("Request : " + request);
			System.out.println("Respon : " + response);
			rsp = response.body().string();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return rsp;
	}

	/*
	 * public static String getFee(FmtReqFee reqFee) { String rsp = ""; String
	 * full_url = "";
	 * 
	 * try { full_url = SysUtils.URL_PORT_PATH + SysUtils.METHOD_GETFEE;
	 * System.out.println("URL : " + full_url);
	 * 
	 * rsp = callAPI(full_url,reqFee.toString()); // OkHttpClient client = new
	 * OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS) //
	 * .readTimeout(30,TimeUnit.SECONDS) // .writeTimeout(30,
	 * TimeUnit.SECONDS).build(); // // // MediaType mediaType =
	 * MediaType.parse("application/json"); // RequestBody body =
	 * RequestBody.create(mediaType,reqFee.toString()); // Request request = new
	 * Request.Builder() // .url(full_url) // .method("POST", body) //
	 * .addHeader("Content-Type", "application/json") // .addHeader("Accept",
	 * "application/json") // .addHeader("Authorization", "Bearer "+SysUtils.token)
	 * // .addHeader("X-POS-USER", "crossborder") // .addHeader("X-POS-PASSWORD",
	 * "crossborder@123") // .build(); // Response response =
	 * client.newCall(request).execute(); // // // System.out.println("Respon : " +
	 * response); // rsp = response.body().string(); // // JSONObject jsonObj = new
	 * JSONObject(rsp); // JSONArray dataArr = jsonObj.getJSONArray("data"); // //
	 * for (int i = 0; i < dataArr.length(); i++) { // JSONObject object =
	 * dataArr.getJSONObject(i); // } // // System.out.println("jsonArr : " +
	 * SysUtils.token);
	 * 
	 * } catch(ClientHandlerException ce) { logback.error(ce.getMessage());
	 * ce.printStackTrace(); } catch (Exception e) { logback.error(e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * return rsp; }
	 * 
	 */
}
