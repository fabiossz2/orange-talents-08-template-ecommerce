package br.com.zupacademy.fabio.ecommerce.service;

import br.com.zupacademy.fabio.ecommerce.commons.email.MlSendMailCompra;
import br.com.zupacademy.fabio.ecommerce.commons.email.MlSendMailCompraFail;
import br.com.zupacademy.fabio.ecommerce.entity.Compra;
import br.com.zupacademy.fabio.ecommerce.entity.EventoCompraSucesso;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventosNovaCompra {
    private Set<EventoCompraSucesso> eventoCompraSucesso;
    private MlSendMailCompra mlSendMailCompra;
    private MlSendMailCompraFail mlSendMailCompraFail;

    public EventosNovaCompra(Set<EventoCompraSucesso> eventoCompraSucesso, MlSendMailCompra mlSendMailCompra,
                             MlSendMailCompraFail mlSendMailCompraFail) {
        this.eventoCompraSucesso = eventoCompraSucesso;
        this.mlSendMailCompra = mlSendMailCompra;
        this.mlSendMailCompraFail = mlSendMailCompraFail;
    }

    public void processa(Compra compra) {
        if (compra.processadaComSucesso()) {
            eventoCompraSucesso.forEach(evento -> evento.processa(compra));
            mlSendMailCompra.enviaEmail(compra, compra.getComprador().getUsername());
        }
        else{
            mlSendMailCompraFail.enviaEmail(compra, compra.getComprador().getUsername());
        }
    }
}
