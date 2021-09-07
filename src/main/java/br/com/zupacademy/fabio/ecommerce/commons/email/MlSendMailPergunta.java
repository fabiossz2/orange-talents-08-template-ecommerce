package br.com.zupacademy.fabio.ecommerce.commons.email;

import br.com.zupacademy.fabio.ecommerce.entity.PerguntaProduto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MlSendMailPergunta implements IMercadoLivreMail {

    private static final Logger logger = LoggerFactory.getLogger(MlSendMailPergunta.class);

    private MailSender mailSender;

    public MlSendMailPergunta(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public <T> boolean enviaEmail(T report, String email) {
        PerguntaProduto p = (PerguntaProduto) report;
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(email);
        simpleMessage.setSubject("Nova pergunta para o produto: " + p.getProduto().getDescricao());
        simpleMessage.setText("Olá você tem uma nova pergunta para o seu produto");
        try {
            this.mailSender.send(simpleMessage);
            return true;
        } catch (MailException e) {
            logger.error("Não foi possível enviar o email para " + email);
        }
        return false;
    }
}
