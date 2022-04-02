package com.example.expenseit.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @PostMapping("register")
    public String registerUser(@RequestBody Map<String, Object> requestMap){
        String firstName = (String) requestMap.get("first_name");
        String secondName = (String) requestMap.get("second_name");
        String thirdName = (String) requestMap.get("third_name");
        String password = (String) requestMap.get("password");
        String email = (String) requestMap.get("email");

        return firstName + secondName + thirdName + password + email;
    }


}
