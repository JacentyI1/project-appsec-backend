package com.jack.webapp.services.v1;


public interface EmailSenderService {

    public void sendEmail(String toEmail, String subject, String body);

}
