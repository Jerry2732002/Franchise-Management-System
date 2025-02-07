package com.example.Franchise.Management.System.service;

import com.example.Franchise.Management.System.enums.Role;
import com.example.Franchise.Management.System.exception.InvalidTokenException;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@Component
public class TokenHandler {
    private static final String secretKey = "GV46Q7HS4L3RTGS";

    private static String hashPayload(String payload) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha.digest((payload + secretKey).getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String sessionId, String userId, Role role) {

        String payload = sessionId + "~" + userId + "~" + role;

        payload += "~" + System.currentTimeMillis();

        String signature = hashPayload(payload);
        return Base64.getEncoder().encodeToString((payload + "~" + signature).getBytes());
    }

    public boolean validateToken(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] args = decodedToken.split("~");
        if (args.length != 4) {
            throw new InvalidTokenException("Invalid Token");
        }
        long tokenCreationTime = Long.parseLong(args[2]);

        if (tokenCreationTime + 1800000 <= System.currentTimeMillis()) {
            throw new InvalidTokenException("Token Expired");
        }
        String tokenSignature = args[3];
        String currentSignature = hashPayload(args[0] + "~" + args[1] + "~" + args[2]);
        if (!tokenSignature.equals(currentSignature)) {
            throw new InvalidTokenException("Token Modified");
        }

        return true;
    }

    public String extractSessionID(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] args = decodedToken.split("~");
        return args[0];
    }

    public String extractUserId(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] args = decodedToken.split("~");
        return args[1];
    }

    public String extractRole(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] args = decodedToken.split("~");
        return args[2];
    }
}

