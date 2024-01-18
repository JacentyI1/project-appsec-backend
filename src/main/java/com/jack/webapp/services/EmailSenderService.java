package com.jack.webapp.services;


public interface EmailSenderService {

    public void sendEmail(String toEmail, String subject, String body);

}
