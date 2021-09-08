package br.com.zupacademy.fabio.ecommerce.controller;

import br.com.zupacademy.fabio.ecommerce.commons.email.MlSendMailCompra;
import br.com.zupacademy.fabio.ecommerce.controller.form.CompraForm;
import br.com.zupacademy.fabio.ecommerce.controller.form.RetornoPagSeguroForm;
import br.com.zupacademy.fabio.ecommerce.entity.*;
import br.com.zupacademy.fabio.ecommerce.entity.enumeration.GatewayPagamento;
import br.com.zupacademy.fabio.ecommerce.repository.CompraRepository;
import br.com.zupacademy.fabio.ecommerce.repository.ProdutoRepository;
import br.com.zupacademy.fabio.ecommerce.service.EventosNovaCompra;
import br.com.zupacademy.fabio.ecommerce.validator.ExistsId;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/compras")
@Validated
public class CompraController {

    private final ProdutoRepository produtoRepository;
    private final CompraRepository compraRepository;
    private final MlSendMailCompra mlSendMailCompra;
    private final EventosNovaCompra eventosNovaCompra;

    public CompraController(ProdutoRepository produtoRepository, CompraRepository compraRepository,
                            MlSendMailCompra mlSendMailCompra, EventosNovaCompra eventosNovaCompra) {
        this.produtoRepository = produtoRepository;
        this.compraRepository = compraRepository;
        this.mlSendMailCompra = mlSendMailCompra;
        this.eventosNovaCompra = eventosNovaCompra;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.FOUND)
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

    @PostMapping(value = "/retorno-pagseguro/{id}")
    @Transactional
    public String processamentoPagSeguro(@PathVariable("id")
                                         @ExistsId(domainClass = Compra.class, fieldName = "id") Long idCompra,
                                         @Valid @RequestBody RetornoPagSeguroForm pagSeguroForm) {
        Compra compra = compraRepository.findCompraById(idCompra);
        compra.tentativaPagamento(pagSeguroForm);
        compraRepository.save(compra);
        eventosNovaCompra.processa(compra);
        return compra.toString();
    }

    @PostMapping(value = "/retorno-paypal/{id}")
    @Transactional
    public String processamentoPaypal(@PathVariable("id")
                                      @ExistsId(domainClass = Compra.class, fieldName = "id") Long idCompra,
                                      @Valid @RequestBody RetornoGatewayPagamento paypalForm) {
        Compra compra = compraRepository.findCompraById(idCompra);
        compra.tentativaPagamento(paypalForm);
        compraRepository.save(compra);
        eventosNovaCompra.processa(compra);
        return compra.toString();
    }

}
