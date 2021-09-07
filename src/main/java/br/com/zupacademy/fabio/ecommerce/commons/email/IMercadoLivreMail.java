package br.com.zupacademy.fabio.ecommerce.commons.email;

public interface IMercadoLivreMail {
    public <T> boolean enviaEmail(T report, String email);
}
