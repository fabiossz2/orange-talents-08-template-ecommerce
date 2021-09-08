package br.com.zupacademy.fabio.ecommerce.entity;

import br.com.zupacademy.fabio.ecommerce.controller.form.CaracteristicasForm;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @Positive
    @NotNull
    private BigDecimal valor;

    @Column(nullable = false)
    @Positive
    private int quantidade;

    @Size(min = 3)
    @Valid
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<CaracteristicasProduto> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @Column(nullable = false, length = 1000)
    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @ManyToOne
    @NotNull
    @Valid
    private Categoria categoria;

    private LocalDateTime dataHoraCadastro;

    @ManyToOne
    @NotNull
    @Valid
    private Usuario dono;

    @OneToMany(mappedBy = "produto")
    @OrderBy("titulo asc")
    private SortedSet<PerguntaProduto> perguntas = new TreeSet<>();

    @OneToMany(mappedBy = "produto")
    private Set<OpiniaoProduto> opinioes = new HashSet<>();


    @Deprecated
    protected Produto() {
    }

    public Produto(@NotBlank String nome, @Positive @NotNull BigDecimal valor, @Positive int quantidade,
                   @NotBlank @Length(max = 1000) String descricao, @NotNull @Valid Categoria categoria,
                   @Size(min = 3) @Valid Collection<CaracteristicasForm> caracteristicas, @NotNull @Valid Usuario dono) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dataHoraCadastro = LocalDateTime.now();
        this.dono = dono;

        Set<CaracteristicasProduto> caracteristicasProdutoSet = caracteristicas
                .stream().map(caracteristica -> caracteristica.converterTo(this)).collect(Collectors.toSet());
        this.caracteristicas.addAll(caracteristicasProdutoSet);

        Assert.isTrue(this.caracteristicas.size() >= 3, "Todo produto precisa ter no mínimo 3 caracteristicas");
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Set<CaracteristicasProduto> getCaracteristicas() {
        return caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Usuario getDono() {
        return dono;
    }

    public <T> Set<T> mapeiaImagens(Function<ImagemProduto, T> funcaoMapeadora) {
        return this.imagens.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    public <T extends Comparable<T>> SortedSet<T> mapeiaPerguntas(Function<PerguntaProduto, T> funcaoMapeadora) {
        return this.perguntas.stream().map(funcaoMapeadora).collect(Collectors.toCollection(TreeSet::new));
    }

    public <T> Set<T> mapeiaOpinioes(Function<OpiniaoProduto, T> funcaoMapeadora) {
        return this.opinioes.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(nome, produto.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

    public boolean abaterEstoque(@Positive int quantidade) {
        Assert.isTrue(quantidade > 0, "a quantidade deve ser maior que zero para abater o estoque" + quantidade);
        if(quantidade <= this.quantidade){
            this.quantidade -= quantidade;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                '}';
    }
}
