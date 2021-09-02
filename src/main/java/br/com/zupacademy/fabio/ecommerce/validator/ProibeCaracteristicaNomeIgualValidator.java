package br.com.zupacademy.fabio.ecommerce.validator;

import br.com.zupacademy.fabio.ecommerce.controller.form.ProdutoForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ProibeCaracteristicaNomeIgualValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ProdutoForm.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()){
            return ;
        }

        ProdutoForm produtoForm = (ProdutoForm) target;

       Set<String> nomesIguais = produtoForm.buscaCaracteristicasIguais();
        if(!nomesIguais.isEmpty()){
            errors.rejectValue("caracteristicas", null, "caracteristicas iguais " + nomesIguais);
        }
    }
}
