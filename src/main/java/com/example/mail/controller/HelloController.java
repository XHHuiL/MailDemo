package com.example.mail.controller;


import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@RestController
@CrossOrigin(origins = "*")
public class HelloController {


    @PostMapping(value = "/hello/{emailAddress}")
    public void sayHello(@PathVariable String emailAddress) {
        String from = "3293628359@qq.com";
        String code = "nrehbbdfcoiydaag";

        // 设置QQ邮件服务器
        String host = "smtp.qq.com";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");

        try {
            // 开启SSL加密
            MailSSLSocketFactory sslSocketFactory = new MailSSLSocketFactory();
            sslSocketFactory.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sslSocketFactory);

            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, code);
                }
            });

            // 发送邮件
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            message.setSubject("Test Java Mail API");
            String content = "<p>测试Java邮件API</p>";
            message.setContent(content, "text/html;charset=UTF-8");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}