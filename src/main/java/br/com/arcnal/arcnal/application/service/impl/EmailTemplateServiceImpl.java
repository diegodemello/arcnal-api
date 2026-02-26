package br.com.arcnal.arcnal.application.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailTemplateServiceImpl {
    private final SpringTemplateEngine springTemplateEngine;

    public String gerarEmailRecuperacao(String nome, String link){
        Context context = new Context();
        context.setVariable("nome", nome);
        context.setVariable("link", link);

        return springTemplateEngine.process("email/recuperacao-de-senha", context);
    }
}
