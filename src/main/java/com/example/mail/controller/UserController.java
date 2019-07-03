package com.example.mail.controller;


import com.example.mail.entity.request.Register;
import com.example.mail.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/register")
    public String register(@RequestBody Register register) {
        if (userService.register(register.getUsername(), register.getPassword(), register.getEmail())) {
            return "Register Success";
        } else {
            return "Register Fail";
        }
    }

    @GetMapping(value = "/user/activate")
    public String activate(@RequestParam(value = "code") String code) {
        if (userService.activate(code)) {
            return "Activate Success";
        } else {
            return "Activate Fail";
        }
    }

}