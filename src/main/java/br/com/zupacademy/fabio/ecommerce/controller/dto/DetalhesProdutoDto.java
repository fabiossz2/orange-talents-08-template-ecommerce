package br.com.zupacademy.fabio.ecommerce.controller.dto;


import br.com.zupacademy.fabio.ecommerce.entity.ImagemProduto;
import br.com.zupacademy.fabio.ecommerce.entity.OpiniaoProduto;
import br.com.zupacademy.fabio.ecommerce.entity.PerguntaProduto;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

public class DetalhesProdutoDto {
    private BigDecimal preco;
    private String nome;
    private String descricao;
    private Set<DetalheProdutoCaracteristicaDto> caracteristicas;
    private Set<String> linksImagens;
    private SortedSet<String> perguntas;
    private Set<Map<String, String>> opinioes;
    private double mediaNotas;

    public DetalhesProdutoDto(Produto produto) {
        this.descricao = produto.getDescricao();
        this.nome = produto.getNome();
        this.preco = produto.getValor();
        this.caracteristicas = produto.getCaracteristicas().stream()
                .map(DetalheProdutoCaracteristicaDto::new).collect(Collectors.toSet());
        this.linksImagens = produto.mapeiaImagens(ImagemProduto::getUrl);
        this.perguntas = produto.mapeiaPerguntas(PerguntaProduto::getTitulo);
        this.opinioes = produto.mapeiaOpinioes(opiniao -> Map.of("titulo", opiniao.getTitulo(), "descricao",
                opiniao.getDescricao()));
        Set<Integer> notas = produto.mapeiaOpinioes(OpiniaoProduto::getNota);
        OptionalDouble media = notas.stream().mapToInt(nota -> nota).average();
        this.mediaNotas = media.orElseGet(() -> 0.0);
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }

    public Set<DetalheProdutoCaracteristicaDto> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<String> getLinksImagens() {
        return linksImagens;
    }

    public SortedSet<String> getPerguntas() {
        return perguntas;
    }

    public Set<Map<String, String>> getOpinioes() {
        return opinioes;
    }

    public double getMediaNotas() {
        return mediaNotas;
    }
}
