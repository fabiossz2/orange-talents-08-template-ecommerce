package br.com.zupacademy.fabio.ecommerce.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "ranking", url = "${ranking.url.externo}")
public interface RankingEndPoint {

    @PostMapping
    void processaRanking(@Valid @RequestBody RankingNovaCompraRequest ranking);
}
