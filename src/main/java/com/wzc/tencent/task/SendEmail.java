package com.wzc.tencent.task;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;


public class SendEmail {
	private static final String SMTPHOST = "smtp.126.com";
	//private static final String PORT = "25";
	//private static final String SSL_FACTORY ="javax.net.ssl.SSLSocketFactory";
	private static final String EMAILNAME = "wzc789376152@126.com";
	private static final String EMAILPASSWORD = "wzc327615213";//"zutlpidxyhjsbjed";
	
	public static void sendTxtMail(String toUser, String context) throws Exception {
		Properties props = new Properties();
			
		//props.setProperty("mail.smtp.host",SMTPHOST);        //指定SMTP服务器  

        //props.setProperty("mail.smtp.auth","false");          //指定是否需要SMTP验证  
        
        //props.setProperty("mail.smtp.port", PORT); //指定端口
       
        //props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);//ssl加密
        
        //props.setProperty("mail.smtp.socketFactory.port", PORT);
        
       //props.setProperty("mail.smtp.socketFactory.fallback","false");
        
		Session session = Session.getInstance(props, null);
		
		
		//session.setDebug(true);// 打开debug模式，会打印发送细节到console
		 
		Message message = new MimeMessage(session); // 实例化一个MimeMessage集成自abstract
		message.setFrom(new InternetAddress(MimeUtility.encodeText("杨梦痕","UTF-8",null)+"<"+EMAILNAME+">")); // 设置发出方,使用setXXX设置单用户，使用addXXX添加InternetAddress[]

		message.setContent(context,"text/html;charset=utf-8"); // 设置文本内容
		// 单一文本使用setText,Multipart复杂对象使用setContent

		message.setSubject(MimeUtility.encodeText("欢迎访问我的个人网站","UTF-8",null)); // 设置标题

		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toUser)); // 设置接收方
		/**
		 * 使用静态方法每次发送需要建立一个到smtp服务器的链接，你可以手动控制连接状态
		 * ，通过session获得tansport，连接到mailserver，而session就可以使用Session.
		 * getDefaultInstance(props,null);获得
		 */
		Transport transport = session.getTransport("smtp");
		System.out.println("sendEmailto:---->"+toUser);
		transport.connect(SMTPHOST, EMAILNAME, EMAILPASSWORD);
		transport.sendMessage(message, message.getAllRecipients());
		System.out.println("success");
	}

	public static void main(String[] args) throws Exception {
		float f = 111111123413f;
		System.out.println(f);
	}
}
