package br.com.zupacademy.fabio.ecommerce.commons.email;

import br.com.zupacademy.fabio.ecommerce.entity.Compra;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MlSendMailCompraFail implements IMercadoLivreMail {

    private static final Logger logger = LoggerFactory.getLogger(MlSendMailCompraFail.class);

    @Value("{endpoint.compra}")
    private String urlCompras;
    private MailSender mailSender;

    public MlSendMailCompraFail(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public <T> boolean enviaEmail(T report, String emailComprador) {
        Compra compra = (Compra) report;
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(emailComprador);
        simpleMessage.setSubject("Olá você tem uma nova mensagem");
        simpleMessage.setText("compra não concluída, segue link para nova tentativa " + this.urlCompras);
        try {
            this.mailSender.send(simpleMessage);
            return true;
        } catch (MailException e) {
            logger.error("Não foi possível enviar o email para " + emailComprador);
        }
        return false;
    }
}
