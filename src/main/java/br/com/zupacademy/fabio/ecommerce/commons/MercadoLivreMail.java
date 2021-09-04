package br.com.zupacademy.fabio.ecommerce.commons;

import br.com.zupacademy.fabio.ecommerce.entity.PerguntaProduto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MercadoLivreMail {

    private static final Logger logger = LoggerFactory.getLogger(MercadoLivreMail.class);

    private MailSender mailSender;

    public MercadoLivreMail(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviaEmailNovaPerguntaProduto(PerguntaProduto produto, String emailVendedor) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(emailVendedor);
        simpleMessage.setSubject("Nova pergunta para o produto: " + produto.getProduto().getDescricao());
        simpleMessage.setText("Olá você tem uma nova pergunta para o seu produto");
        try {
            this.mailSender.send(simpleMessage);
        } catch (MailException e) {
            logger.error("Não foi possível enviar o email para " + emailVendedor);
        }
    }
}
