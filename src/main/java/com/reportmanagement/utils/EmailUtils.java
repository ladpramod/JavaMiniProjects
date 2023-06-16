package com.reportmanagement.utils;

import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.internet.MimeMessage;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String subject, String body, String to, File file) {

        try {
            MimeMessage mimeMsg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg,true);
            helper.setSubject(subject);
            helper.setText(body,true);
            helper.setTo(to);
            helper.addAttachment(file.getName(),file);
            mailSender.send(mimeMsg);

        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }
}
