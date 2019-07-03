package com.example.mail.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil implements Runnable {
    // 用户注册邮箱以及激活码
    private String email;
    private String code;

    // 发件人邮箱
    private static final String FROM = "3293628359@qq.com";
    // 发件人邮箱授权码
    private static final String AUTH_CODE = "nrehbbdfcoiydaag";
    // 发件人邮箱主机
    private static final String HOST = "smtp.qq.com";

    public MailUtil(String email, String code) {
        this.email = email;
        this.code = code;
    }

    @Override
    public void run() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", HOST);
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
                    return new PasswordAuthentication(FROM, AUTH_CODE);
                }
            });

            // 发送邮件
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("MailDemo: 激活账户");
            String content =
                    "<html>" +
                            "<body>" +
                            "<h1>这是一封激活邮件,激活请点击以下链接</h1>" +
                            "<h3>" +
                            "<a target='_blank' href='http://localhost/user/active?code=" + code + "'>http://localhost/user/active?code=" + code + "</a>" +
                            "</h3>" +
                            "</body>" +
                            "</html>";
            message.setContent(content, "text/html;charset=UTF-8");
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}