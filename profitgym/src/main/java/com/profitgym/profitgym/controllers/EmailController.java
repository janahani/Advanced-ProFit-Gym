package com.profitgym.profitgym.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.profitgym.profitgym.models.EmailData;


@RestController
public class EmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailData emailData) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailData.getRecipient());
        message.setSubject(emailData.getSubject());
        message.setText(emailData.getBody());

        try {
            emailSender.send(message);
            return "Email sent successfully!";
        } catch (MailException ex) {
            return "Error sending email: " + ex.getMessage();
        }
    }
}
