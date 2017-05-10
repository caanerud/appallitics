package com.theironyard;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Created by JamesHartanto on 5/10/17.
 */

public class Email {

    public static void main(String[] args) {
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
            message.setFrom(new InternetAddress("")); //Sender's Email Address (FROM)
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("")); //Receiver's Email Address (TO)
            message.setSubject(""); // Subject Line
            message.setText(""); // Body Text

            Transport.send(message);

            System.out.println("Message Sent!"); //Confirmation Method - Not necessary

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
