/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class EmailsActions {
 
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
 
	public static void main(String args[]) throws AddressException, MessagingException {
		generateAndSendEmail();
		System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
	}
 
	public static void generateAndSendEmail() throws AddressException, MessagingException {
 
//Step1		
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
                mailServerProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
//Step2		
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("noeisaipc2@live.com.mx"));
		generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("noeisaipc2@live.com.mx"));
		generateMailMessage.setSubject("Greetings from Crunchify..");
		String emailBody = "Test email by Crunchify.com JavaMail API example. " + "<br><br> Regards, <br>Crunchify Admin";
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session has been created successfully..");
 
//Step3		
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");
		
		// Enter your correct gmail UserID and Password (XXXApp Shah@gmail.com)
		transport.connect("smtp.gmail.com", "movecoins@gmail.com", "Noe199710");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}