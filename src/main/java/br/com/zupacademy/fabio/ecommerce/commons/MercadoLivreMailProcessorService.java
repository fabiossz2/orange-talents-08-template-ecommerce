package br.com.zupacademy.fabio.ecommerce.commons;

import br.com.zupacademy.fabio.ecommerce.entity.PerguntaProduto;
import br.com.zupacademy.fabio.ecommerce.repository.PerguntaRepository;
import org.springframework.stereotype.Service;

@Service
public class MercadoLivreMailProcessorService {

    private MercadoLivreMail mercadoLivreMail;
    private PerguntaRepository perguntaRepository;

    public MercadoLivreMailProcessorService(MercadoLivreMail mercadoLivreMail, PerguntaRepository perguntaRepository) {
        this.mercadoLivreMail = mercadoLivreMail;
        this.perguntaRepository = perguntaRepository;
    }

    public void execute(PerguntaProduto pergunta, String emailVendedor) {
        this.perguntaRepository.save(pergunta);
        this.mercadoLivreMail.enviaEmailNovaPerguntaProduto(pergunta, emailVendedor);
    }
}
