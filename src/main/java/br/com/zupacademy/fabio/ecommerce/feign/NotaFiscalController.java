package br.com.zupacademy.fabio.ecommerce.feign;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notas-fiscais")
public class NotaFiscalController {

    @PostMapping
    public void processaNF(@RequestBody NfRequest request) {
        System.out.println("GERANDO NOTA FISCAL:");
        System.out.println(request.toString());
    }
}
