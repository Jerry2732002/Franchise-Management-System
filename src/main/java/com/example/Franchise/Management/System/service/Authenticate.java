package com.example.Franchise.Management.System.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;


@Component
public class Authenticate {

    private String salt;

    public Authenticate() {
        generateSalt();
    }

    private void generateSalt() {
        salt = BCrypt.gensalt(12);
    }

    public String encodePassword(String password) {
        return BCrypt.hashpw(password, salt);
    }

    public boolean checkPassword(String password, String encoded_password) {
        return BCrypt.checkpw(password, encoded_password);
    }

}
