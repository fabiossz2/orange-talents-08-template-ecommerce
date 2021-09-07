package br.com.zupacademy.fabio.ecommerce.entity.enumeration;

import br.com.zupacademy.fabio.ecommerce.entity.Compra;
import org.springframework.web.util.UriComponentsBuilder;

public enum GatewayPagamento {
    PAGSEGURO {
        @Override
        public String getUrl(Compra compra, UriComponentsBuilder builder) {
            String urlPagseguro = builder.path("/retorno-pagseguro/{id}")
                    .buildAndExpand(compra.getId()).toString();
            return "pagseguro.com/" + compra.getId() + "?redirectUrl=" + urlPagseguro;
        }
    }, PAYPAL {
        @Override
        public String getUrl(Compra compra, UriComponentsBuilder builder) {
            final String urlPaypal = builder.path("/retorno-paypal/{id}").buildAndExpand(compra.getId()).toString();
            return "paypal.com/" + compra.getId() + "?redirectUrl=" + urlPaypal;
        }
    };

    public abstract String getUrl(Compra compra, UriComponentsBuilder builder);
}
