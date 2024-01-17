package com.jack.webapp.services.v1.impl;

import com.jack.webapp.services.v1.EmailSenderService;
import lombok.extern.java.Log;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log
public class EmailSenderServiceImpl implements EmailSenderService {

    private JavaMailSender mailSender;

    public void sendEmail(String toEmail,
                          String subject,
                          String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("appsecproject@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        log.info("Mail sent successfully...");
    }
}
