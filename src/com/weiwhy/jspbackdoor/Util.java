package com.weiwhy.jspbackdoor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Util {
	private static OkHttpClient okHttpClient
	=new OkHttpClient.Builder().build();
	public static String Readtext(String name) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(name)));
			StringBuffer data=new StringBuffer();
			String tmp="";
			while ((tmp = bufferedReader.readLine()) != null) {
				data.append(tmp+"\n");
			}
			bufferedReader.close();
			return data.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	
	public static String DoCode(String pfm) {
		switch (pfm) {
		case "win":
			
			break;
		case "unix":
			break;
		default:
			break;
		}
	}
	
	public static String GetResponseString(String pass,String url,String content,String key,String words) {
		FormBody.Builder formbuild=new FormBody.Builder();
		formbuild.add(pass, content);
		formbuild.add("key", key);
		formbuild.add("words", words);
		
		Request request = new Request.Builder()
				.url(url)
				.post(formbuild.build())
				.build();
		Response response;
		try {
			response = okHttpClient.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}
	
	public static int GetStatuscode(String pass,String url,String content,String key,String words) {
		FormBody.Builder formbuild=new FormBody.Builder();
		formbuild.add(pass, content);
		formbuild.add("key", key);
		formbuild.add("words", words);
		
		Request request = new Request.Builder()
				.url(url)
				.post(formbuild.build())
				.build();
		Response response;
		try {
			response = okHttpClient.newCall(request).execute();
			response.body();
			return response.code();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 404;
		}
	}
}
