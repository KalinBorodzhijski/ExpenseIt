package com.example.expenseit.repositories;


import com.example.expenseit.errors.AuthException;
import com.example.expenseit.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    Integer countByEmail(String email);
    Client findByEmailAndPassword(String email, String password);
}
