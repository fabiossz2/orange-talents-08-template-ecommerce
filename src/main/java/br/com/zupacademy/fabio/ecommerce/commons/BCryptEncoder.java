package br.com.zupacademy.fabio.ecommerce.commons;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptEncoder {

    public static String enconderPassword(String pass) {
        return new BCryptPasswordEncoder().encode(pass);
    }
}
