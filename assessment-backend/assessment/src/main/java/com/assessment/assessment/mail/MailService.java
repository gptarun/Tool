package com.assessment.assessment.mail;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.assessment.assessment.session.Session;
import com.assessment.assessment.session.SessionDAO;

@CrossOrigin(origins = "${origin}")
@Controller
public class MailService {
	@Autowired
	private JavaMailSender sender;
	@Autowired
	private SessionDAO sDao;

	@Value("${origin}")
	private String origin;

	@Value("${foorter.image}")
	private String footerImage;

	@Value("${header.image}")
	private String headerImage;

	@Value("${button.image}")
	private String buttonImage;

	@PutMapping("/{session}/outemail")
	public String outcomeMail(@PathVariable String session, @RequestBody Mail mail) throws Exception {
		try {
			Session emailSession = sDao.getEmailByUrl(session);
			mail.setRecipient(emailSession.getEmail());
			sendEmail(mail, emailSession.getTeamName());
			return "Email Sent";
		} catch (Exception e) {
			throw e;
		}
	}

	private void sendEmail(Mail mail, String teamName) throws Exception {
		MimeMessage message = sender.createMimeMessage();
		//MimeMessage message = new MimeMessage(MailConnection.getMailConfig());
		message.setRecipients(Message.RecipientType.TO, mail.getRecipient());
		message.setFrom("");
		message.setSubject("Congratulations On Evaluating An Area!");
		String htmlText = "<table><th><tr><img src=\"cid:headerimage\"></tr></th><tr><td><p style=\"font-family:AmericanSans,sans-serif;\" accept-charset=\"UTF-8\">Hi "
				+ teamName
				+ ",<br><br>Great news! Your lates evaluated area is now available for review via the<br><br><a href=\""
				+ mail.getUrl() + "\"><img src=\"cid:buttonimage\"></a><br><br>"
				+ "NOTE: When you evaluate an additional area, enter the same team name " + teamName
				+ " to connect your aggregate data in the Assessment.</p></td></tr></table>"
				+ "<footer><a href=\"www.google.com\"><img src=\"cid:footerimage\"></a></footer>";

		MimeMultipart multipart = new MimeMultipart("related");

		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(htmlText, "text/html");
		multipart.addBodyPart(messageBodyPart);
		
		// adding footer image to message body part
		messageBodyPart = new MimeBodyPart();
		DataSource foorterDS = new FileDataSource(getFooterImage());
		messageBodyPart.setDataHandler(new DataHandler(foorterDS));
		messageBodyPart.setHeader("Content-ID", "<footerimage>");
		messageBodyPart.setDisposition(Part.INLINE);
		multipart.addBodyPart(messageBodyPart);

		// adding header image to message body part
		messageBodyPart = new MimeBodyPart();
		DataSource headerDS = new FileDataSource(getHeaderImage());
		messageBodyPart.setDataHandler(new DataHandler(headerDS));
		messageBodyPart.setHeader("Content-ID", "<headerimage>");
		messageBodyPart.setDisposition(Part.INLINE);
		multipart.addBodyPart(messageBodyPart);

		// adding button image to message body part
		messageBodyPart = new MimeBodyPart();
		DataSource buttonDS = new FileDataSource(getButtonImage());
		messageBodyPart.setDataHandler(new DataHandler(buttonDS));
		messageBodyPart.setHeader("Content-ID", "<buttonimage>");
		messageBodyPart.setDisposition(Part.INLINE);
		multipart.addBodyPart(messageBodyPart);
		
		// put everything together
		message.setContent(multipart);

		// This is for testing purpose
		// MailConnection.sentMailTransport(message);
		sender.send(message);
	}

	private File getFooterImage() throws URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();
		URI imageURI = classLoader.getResource(footerImage).toURI();
		File imageFile = new File(imageURI);
		File newImageFile = new File(imageFile.getAbsolutePath());
		return newImageFile;
	}

	private File getHeaderImage() throws URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();
		URI imageURI = classLoader.getResource(headerImage).toURI();
		File imageFile = new File(imageURI);
		File newImageFile = new File(imageFile.getAbsolutePath());
		return newImageFile;
	}

	private File getButtonImage() throws URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();
		URI imageURI = classLoader.getResource(buttonImage).toURI();
		File imageFile = new File(imageURI);
		File newImageFile = new File(imageFile.getAbsolutePath());
		return newImageFile;

	}

}
