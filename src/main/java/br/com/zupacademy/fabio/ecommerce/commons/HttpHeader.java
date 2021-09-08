package br.com.zupacademy.fabio.ecommerce.commons;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class HttpHeader {
    public static HttpHeaders builder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
