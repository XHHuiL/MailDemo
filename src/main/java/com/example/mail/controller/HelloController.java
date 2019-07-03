package com.example.mail.controller;


import com.example.mail.util.MailUtil;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class HelloController {


    @PostMapping(value = "/hello/{emailAddress}")
    public void sayHello(@PathVariable String emailAddress) {
        new Thread(new MailUtil(emailAddress, "Test Code")).start();
    }

}