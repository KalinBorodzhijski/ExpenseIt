package com.example.expenseit.controllers;

import com.example.expenseit.models.Client;
import com.example.expenseit.models.dto.ClientDTO;
import com.example.expenseit.services.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping("login")
    public ResponseEntity<Map<String,String>> loginClient(@RequestBody Map<String, Object> requestMap){
        String email = (String) requestMap.get("email");
        String password = (String) requestMap.get("password");
        Client client = clientService.logInClient(email,password);
        Map<String, String> map = new HashMap<>();
        map.put(client.getFirstName(), "LogIn successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @PostMapping("register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody String clientJSON) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClientDTO clientDTO = objectMapper.readValue(clientJSON, ClientDTO.class);
        Client client = clientService.registerNewClient(clientDTO);
        Map<String,String> map = new HashMap<>();
        map.put("message", "registered successfully");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
