package com.example.mail.service;

public interface UserService {

    boolean register(String username, String password, String email);

    boolean active(String code);

}
