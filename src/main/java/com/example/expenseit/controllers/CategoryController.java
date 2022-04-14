package com.example.expenseit.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping
    public String getAllCategories(HttpServletRequest request){
        int clientId = (int)request.getAttribute("clientId");

        //TODO: Added here just for test. Will be fixed later.
        return "ID: " + clientId;
    }
}
