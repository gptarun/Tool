package com.assessment.assessment.mail;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class MailConnection {
	final static String username = "tarung1201@gmail.com";
	final static String password = "Kishan!3110";
	public static Session getMailConfig() {
		
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "25"); 
        props.put("mail.smtp.auth", "true");
		
        Session ses = Session.getDefaultInstance(props, null);
		
		return ses;
	}
	
	public static void sentMailTransport(MimeMessage message) throws MessagingException {
		Transport transport = getMailConfig().getTransport("smtp");
        transport.connect("smtp.gmail.com", username, password);
        transport.sendMessage(message, message.getAllRecipients());
        System.out.println("done");
        transport.close();
	}
}
