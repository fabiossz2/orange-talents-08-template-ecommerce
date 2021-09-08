package br.com.zupacademy.fabio.ecommerce.feign;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    @PostMapping
    public void processaRanking(@RequestBody RankingNovaCompraRequest ranking) {
        System.out.println("RANKING");
        System.out.println(ranking.toString());
    }
}
