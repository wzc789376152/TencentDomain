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
			
		//props.setProperty("mail.smtp.host",SMTPHOST);        //ָ��SMTP������  

        //props.setProperty("mail.smtp.auth","false");          //ָ���Ƿ���ҪSMTP��֤  
        
        //props.setProperty("mail.smtp.port", PORT); //ָ���˿�
       
        //props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);//ssl����
        
        //props.setProperty("mail.smtp.socketFactory.port", PORT);
        
       //props.setProperty("mail.smtp.socketFactory.fallback","false");
        
		Session session = Session.getInstance(props, null);
		
		
		//session.setDebug(true);// ��debugģʽ�����ӡ����ϸ�ڵ�console
		 
		Message message = new MimeMessage(session); // ʵ����һ��MimeMessage������abstract
		message.setFrom(new InternetAddress(MimeUtility.encodeText("���κ�","UTF-8",null)+"<"+EMAILNAME+">")); // ���÷�����,ʹ��setXXX���õ��û���ʹ��addXXX���InternetAddress[]

		message.setContent(context,"text/html;charset=utf-8"); // �����ı�����
		// ��һ�ı�ʹ��setText,Multipart���Ӷ���ʹ��setContent

		message.setSubject(MimeUtility.encodeText("��ӭ�����ҵĸ�����վ","UTF-8",null)); // ���ñ���

		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toUser)); // ���ý��շ�
		/**
		 * ʹ�þ�̬����ÿ�η�����Ҫ����һ����smtp�����������ӣ�������ֶ���������״̬
		 * ��ͨ��session���tansport�����ӵ�mailserver����session�Ϳ���ʹ��Session.
		 * getDefaultInstance(props,null);���
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
