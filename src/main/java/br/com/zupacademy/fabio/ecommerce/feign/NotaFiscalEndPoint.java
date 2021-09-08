package br.com.zupacademy.fabio.ecommerce.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "nf", url = "${notasfiscais.url}")
public interface NotaFiscalEndPoint {

    @PostMapping
    void processaNF(@Valid @RequestBody NfRequest request);
}
