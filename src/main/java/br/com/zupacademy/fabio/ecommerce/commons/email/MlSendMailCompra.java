package br.com.zupacademy.fabio.ecommerce.commons.email;

import br.com.zupacademy.fabio.ecommerce.entity.Compra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MlSendMailCompra implements IMercadoLivreMail {

    private static final Logger logger = LoggerFactory.getLogger(MlSendMailCompra.class);

    private MailSender mailSender;

    public MlSendMailCompra(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public <T> boolean enviaEmail(T report, String emailComprador) {
        Compra compra = (Compra) report;
        final String emailVendedor = compra.getProdutoEscolhido().getDono().getUsername();
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(emailVendedor);
        simpleMessage.setSubject("Olá você tem uma nova venda");
        simpleMessage.setText("O usuário " + emailComprador + "realmente disse que queria comprar seu produto "
                + compra.getProdutoEscolhido());
        try {
            this.mailSender.send(simpleMessage);
            return true;
        } catch (MailException e) {
            logger.error("Não foi possível enviar o email para " + emailVendedor);
        }
        return false;
    }
}
