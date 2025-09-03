package edu.ubb.biblioSpring.backend.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncrypter {
    public String encrypt(String password, String salt){
        try{
            MessageDigest digest=MessageDigest.getInstance("SHA-256");

            byte[] input=(password+salt).getBytes();
            digest.reset();
            digest.update(input);

            byte[] output= digest.digest();
            StringBuilder stringBuilder= new StringBuilder();
            for(byte b:output){
                stringBuilder.append(String.format("%02x",b));
            }

            return stringBuilder.toString();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}
