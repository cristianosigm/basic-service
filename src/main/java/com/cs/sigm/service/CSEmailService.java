package com.cs.sigm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class CSEmailService {

    @Value("${climate-service.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender sender;

    public void sendEmail(String to, String subject, String content) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        sender.send(message);
    }

}
