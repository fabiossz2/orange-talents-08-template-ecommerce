package br.com.zupacademy.fabio.ecommerce.controller;

import br.com.zupacademy.fabio.ecommerce.commons.MercadoLivreMailProcessorService;
import br.com.zupacademy.fabio.ecommerce.config.security.TokenService;
import br.com.zupacademy.fabio.ecommerce.controller.dto.*;
import br.com.zupacademy.fabio.ecommerce.controller.form.ImagemProdutoRequest;
import br.com.zupacademy.fabio.ecommerce.controller.form.OpiniaoProdutoForm;
import br.com.zupacademy.fabio.ecommerce.controller.form.PerguntaProdutoForm;
import br.com.zupacademy.fabio.ecommerce.controller.form.ProdutoForm;
import br.com.zupacademy.fabio.ecommerce.entity.*;
import br.com.zupacademy.fabio.ecommerce.repository.*;
import br.com.zupacademy.fabio.ecommerce.validator.ExistsId;
import br.com.zupacademy.fabio.ecommerce.validator.ProibeCaracteristicaNomeIgualValidator;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@Validated
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ImagemRepository imagemRepository;
    private final OpiniaoRepository opiniaoRepository;
    private final PerguntaRepository perguntaRepository;
    private final MercadoLivreMailProcessorService mercadoLivreMailProcessorService;
    private final Uploader uploader;

    public ProdutoController(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository,
                             UsuarioRepository usuarioRepository, TokenService tokenService, ImagemRepository imagemRepository,
                             OpiniaoRepository opiniaoRepository, PerguntaRepository perguntaRepository, MercadoLivreMailProcessorService mercadoLivreMailProcessorService, Uploader uploader) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.imagemRepository = imagemRepository;
        this.opiniaoRepository = opiniaoRepository;
        this.perguntaRepository = perguntaRepository;
        this.mercadoLivreMailProcessorService = mercadoLivreMailProcessorService;
        this.uploader = uploader;
    }

    @InitBinder(value = "produtoForm")
    public void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ProibeCaracteristicaNomeIgualValidator());
    }

    @PostMapping
    @Transactional
    public ProdutoDto cria(@RequestBody @Valid ProdutoForm produtoForm, @AuthenticationPrincipal Usuario user) {
        Produto produto = produtoForm.converterProduto(produtoRepository, categoriaRepository, usuarioRepository, user);
        produto = produtoRepository.save(produto);
        return new ProdutoDto(produto);
    }


    @Transactional
    @PostMapping("/{id}/imagens")
    public List<ImagemDto> adicionaImagem(@Valid ImagemProdutoRequest imagemRequest, @AuthenticationPrincipal Usuario user,
                                          @PathVariable(name = "id") Long idProduto) {
        List<ImagemProduto> imagens = imagemRequest.converterToImagens(idProduto, user, produtoRepository, uploader);
        this.imagemRepository.saveAll(imagens);
        return new ImagensDto(imagens).getImagens();
    }

    @Transactional
    @PostMapping("/{id}/opinioes")
    public OpiniaoDto adicionaOpiniao(@Valid @RequestBody OpiniaoProdutoForm form,
                                      @PathVariable(name = "id")
                                      @ExistsId(domainClass = Produto.class, fieldName = "id") Long idProduto,
                                      @AuthenticationPrincipal Usuario user) {
        OpiniaoProduto opiniao = form.adicionaOpiniaoProduto(produtoRepository, user, idProduto);
        opiniao = opiniaoRepository.save(opiniao);
        return new OpiniaoDto(opiniao);
    }


    @PostMapping("/{id}/perguntas")
    public PerguntaDto perguntaSobreOProduto(@Valid @RequestBody PerguntaProdutoForm form,
                                             @PathVariable(name = "id")
                                             @ExistsId(domainClass = Produto.class, fieldName = "id") Long idProduto,
                                             @AuthenticationPrincipal Usuario user) {
        PerguntaProduto pergunta = form.realizaPergunta(user, usuarioRepository, produtoRepository, idProduto);
        final Usuario dono = pergunta.getProduto().getDono();
        final String emailVendedor = dono.getUsername();
        this.mercadoLivreMailProcessorService.execute(pergunta, emailVendedor);
        return new PerguntaDto(pergunta);
    }

    @GetMapping("/{id}")
    public DetalhesProdutoDto detalhesProduto(@PathVariable(name = "id")
                                              @ExistsId(domainClass = Produto.class, fieldName = "id") Long idProduto) {
        final Produto produto = this.produtoRepository.findProdutoById(idProduto);
        return new DetalhesProdutoDto(produto);
    }
}
