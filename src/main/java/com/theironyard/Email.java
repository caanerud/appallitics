package com.theironyard;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Created by JamesHartanto on 5/10/17.
 */

public class Email {

    public static void main(String from, String to, String subject, String msg) {
        final String username = ""; //Enter email login username - IE. name@gmail.com
        final String password = ""; //Enter email login password - IE. 123Password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from)); //Sender's Email Address (FROM)
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to)); //Receiver's Email Address (TO)
            message.setSubject(subject); // Subject Line
            message.setText(msg); // Body Text

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
