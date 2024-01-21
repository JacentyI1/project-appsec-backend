package com.jack.webapp.services.impl;

import com.jack.webapp.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
//    @Autowired
//    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("appsecproject@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        getJavaMailSender().send(message);
    }


    private JavaMailSender getJavaMailSender(){
        return new JavaMailSenderImpl();
    }

}
