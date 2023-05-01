package com.example.notificationgate.scheduler.email;

import com.example.notificationgate.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    public void sendEmail(String to, Event event){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dmharit@yandex.ru");
        message.setTo(to);
        message.setSubject(event.getName());
        message.setText(event.getDescription());
        emailSender.send(message);
    }
}
