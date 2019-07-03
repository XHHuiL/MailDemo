package com.example.mail.service.impl;

import com.example.mail.dao.UserDao;
import com.example.mail.entity.User;
import com.example.mail.entity.UserExample;
import com.example.mail.service.UserService;
import com.example.mail.util.MailUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean register(String username, String password, String email) {
        // 判断邮箱是否合法
        if (!email.matches("^\\w+@(\\w+\\.)+\\w+$")) {
            return false;
        }
        // 生成激活码
        String code = UUID.randomUUID().toString().replaceAll("-", "");
        User user = new User(username, password, email, 0, code);
        // 当注册成功时，开启新的线程向用户发送一封激活邮件
        if (userDao.insert(user) > 0) {
            new Thread(new MailUtil(email, code)).start();
            return true;
        }
        return false;
    }

    @Override
    public boolean activate(String code) {
        // 更新state，激活用户
        User user = new User();
        user.setState(1);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andCodeEqualTo(code);
        return userDao.updateByExampleSelective(user, userExample) > 0;
    }
}