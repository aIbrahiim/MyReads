package com.abdelrahman.myreads.MyReads.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailSenderService {

    @Async
    public void sendEmail(SimpleMailMessage email);
}
