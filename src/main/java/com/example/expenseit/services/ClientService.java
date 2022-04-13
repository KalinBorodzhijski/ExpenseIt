package com.example.expenseit.services;

import com.example.expenseit.errors.AuthException;
import com.example.expenseit.models.Client;
import com.example.expenseit.models.dto.ClientDTO;
import com.example.expenseit.repositories.ClientRepository;
import com.example.expenseit.util.SHA256PasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;

    private static final String EMAIL_VERIFICATION_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public Client logInClient(String email, String password) throws AuthException{

        Client client = clientRepository.findByEmailAndPassword(email,SHA256PasswordEncoder.getSHA(password));
        if(client == null){
            throw new AuthException("Invalid email or password");
        }
        return client;
    }


    public Client registerNewClient(ClientDTO clientDTO) throws AuthException{

        boolean isEmailValid = validateEmail(clientDTO.getEmail());
        if(isEmailValid) {
            Client tempClient = populateClientData(clientDTO);
            return clientRepository.save(tempClient);
        }
        return null;
    }

    private Client populateClientData(ClientDTO clientDTO) {
        Client client = Client.builder()
                .firstName(clientDTO.getFirstName())
                .secondName(clientDTO.getSecondName())
                .thirdName(clientDTO.getThirdName())
                .email(clientDTO.getEmail())
                .password(SHA256PasswordEncoder.getSHA(clientDTO.getPassword()))
                .build();

        return client;
    }

    private boolean validateEmail(String email) throws AuthException {

        Pattern pattern = Pattern.compile(EMAIL_VERIFICATION_REGEX);
        if (email != null) {
            email = email.toLowerCase();
            if (!pattern.matcher(email).matches()) {
                throw new AuthException("Invalid email format!");
            }
            int clientCount = clientRepository.countByEmail(email);

            if (clientCount > 0) throw new AuthException("Email is already in use");

            return true;
        }

        return false;
    }
}
