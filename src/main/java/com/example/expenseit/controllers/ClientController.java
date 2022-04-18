package com.example.expenseit.controllers;

import com.example.expenseit.Constants;
import com.example.expenseit.models.Client;
import com.example.expenseit.models.dto.ClientDTO;
import com.example.expenseit.services.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {


    private final ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping("login")
    public ResponseEntity<Map<String,String>> loginClient(@RequestBody Map<String, Object> requestMap){
        String email = (String) requestMap.get("email");
        String password = (String) requestMap.get("password");
        Client client = clientService.logInClient(email,password);
        return new ResponseEntity<>(generateJWTToken(client), HttpStatus.OK);

    }

    @PostMapping("register")
    public ResponseEntity<Void> registerUser(@RequestBody String clientJSON) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClientDTO clientDTO = objectMapper.readValue(clientJSON, ClientDTO.class);
        Client client = clientService.registerNewClient(clientDTO);
        return new ResponseEntity<>( HttpStatus.OK);
    }


    private Map<String, String> generateJWTToken(Client client){
        long currentTime = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + Constants.JWT_TOKEN_VALID_TIME))
                .claim("clientId",client.getUserId())
                .claim("email",client.getEmail())
                .compact();
        Map<String,String> map = new HashMap<>();
        map.put("token", token);
        return map;
    }

}
