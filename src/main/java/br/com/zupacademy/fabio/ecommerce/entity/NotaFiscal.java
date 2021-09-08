package br.com.zupacademy.fabio.ecommerce.entity;

import br.com.zupacademy.fabio.ecommerce.commons.HttpHeader;
import com.google.gson.Gson;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotaFiscal implements EventoCompraSucesso {

    @Value("${notas.fiscais.url.externo}")
    private String urlNf;

    @Override
    public boolean processa(Compra compra) {
        Assert.isTrue(compra.processadaComSucesso(), "compra nao processada " + compra);
        Gson gson = new Gson();
        HttpHeaders headers = HttpHeader.builder();
        Map<String, Object> mapCompra = new HashMap<String, Object>();
        mapCompra.put("idCompra", compra.getId());
        mapCompra.put("idComprador", compra.getComprador().getId());
        String json = gson.toJson(mapCompra);
        ResponseEntity<String> exchange = new RestTemplate()
                .exchange(urlNf, HttpMethod.POST, new HttpEntity<String>(json, headers), String.class);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return true;
        }
        return false;
    }
}
