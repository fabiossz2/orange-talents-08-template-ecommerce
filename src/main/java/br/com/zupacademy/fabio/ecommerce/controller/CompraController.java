package br.com.zupacademy.fabio.ecommerce.controller;

import br.com.zupacademy.fabio.ecommerce.commons.email.MlSendMailCompra;
import br.com.zupacademy.fabio.ecommerce.controller.form.CompraForm;
import br.com.zupacademy.fabio.ecommerce.entity.Compra;
import br.com.zupacademy.fabio.ecommerce.entity.enumeration.GatewayPagamento;
import br.com.zupacademy.fabio.ecommerce.entity.Produto;
import br.com.zupacademy.fabio.ecommerce.entity.Usuario;
import br.com.zupacademy.fabio.ecommerce.repository.CompraRepository;
import br.com.zupacademy.fabio.ecommerce.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping("/compras")
public class CompraController {

    private final ProdutoRepository produtoRepository;
    private final CompraRepository compraRepository;
    private final MlSendMailCompra mlSendMailCompra;

    public CompraController(ProdutoRepository produtoRepository, CompraRepository compraRepository,
                            MlSendMailCompra mlSendMailCompra) {
        this.produtoRepository = produtoRepository;
        this.compraRepository = compraRepository;
        this.mlSendMailCompra = mlSendMailCompra;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String compra(@RequestBody @Valid CompraForm compraForm, @AuthenticationPrincipal Usuario comprador,
                         UriComponentsBuilder builder)
            throws BindException {
        final Produto produto = produtoRepository.findProdutoById(compraForm.getIdProduto());
        int quantidade = compraForm.getQuantidade();
        final boolean abateu = produto.abaterEstoque(compraForm.getQuantidade());

        if (abateu) {
            final GatewayPagamento gateway = compraForm.getGateway();
            final Compra novaCompra = new Compra(produto, quantidade, comprador, gateway);
            this.compraRepository.save(novaCompra);
            this.mlSendMailCompra.enviaEmail(novaCompra, comprador.getUsername());
            return gateway.getUrl(novaCompra, builder);
        }
        BindException problemaComEstoque = new BindException(compraForm, "compraForm");
        problemaComEstoque.reject(null, "não foi possível realizar a compra por conta do estoque");
        throw problemaComEstoque;
    }
}
