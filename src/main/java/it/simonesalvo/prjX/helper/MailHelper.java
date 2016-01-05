package it.simonesalvo.prjX.helper;

import it.simonesalvo.prjX.application.ApplicationConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by Simone Salvo on 27/12/15.
 * www.simonesalvo.it
 */
public class MailHelper {

    public static String getSenderEmail() {
        return "noreply@" + ApplicationConfig.applicationId + ".appspotmail.com";
    }

    public static final String DISCLAIMER = "<br><br>*** This is an automatic mail. Please do not reply ***";

    /**
     * Send an email on behalf of the application
     *
     * @param recipientEmail the email address of the recipient
     * @param recipientName  the name of the recipient
     * @param subject        the subject of the mail
     * @param body           the body of the mail
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static void sendMail(String recipientEmail,
                                String recipientName,
                                String subject,
                                String body) throws UnsupportedEncodingException, MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        Multipart mp = new MimeMultipart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(body, "text/html");
        mp.addBodyPart(htmlPart);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(getSenderEmail(), ApplicationConfig.APP_SENDER_NAME));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail, recipientName));
        msg.setSubject(subject);
        msg.setContent(mp);
        Transport.send(msg);
    }
}