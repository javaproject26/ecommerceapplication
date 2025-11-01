package com.example.simpleecommerceapp.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {

    public void sendOtpEmail(String to, String otp) {
        String from = "yourexample74@gmail.com"; // Replace with your Gmail
        String password = "uede wrtv rpdy edkq"; // Replace with your 16-digit App Password

        // SMTP configuration
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a Mail session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Compose the email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject("OTP Verification - Ecommerce App");
            message.setText("Your OTP is : " + otp);

            // Send email
            Transport.send(message);

            System.out.println("OTP email sent to: " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void SuccesfulregisteredEmail(String to) {
        String from = "yourexample74@gmail.com"; // Replace with your Gmail
        String password = "uede wrtv rpdy edkq"; // Replace with your 16-digit App Password

        // SMTP configuration
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a Mail session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Compose the email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject("OTP Verification was succesfull  - Ecommerce App");
            message.setText("you have registered succesfully to the ecommerce app with email:"+to);


            // Send email
            Transport.send(message);

            System.out.println("OTP email sent to: " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
