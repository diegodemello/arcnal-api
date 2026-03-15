package br.com.arcnal.arcnal.infra.util;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnvioDeEmail {

    @Value("${resend.api-key}")
    String resendApiKey;

    Resend resend;

    @PostConstruct
    public void init(){
        this.resend = new Resend(resendApiKey);
    }

    public void enviarEmail(String destinatario, String assunto, String html) {
        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("Recuperação de Senha <no-reply@arcnal.com.br>")
                    .to(destinatario)
                    .subject(assunto)
                    .html(html)
                    .build();

            CreateEmailResponse data = resend.emails().send(params);

            log.info("Email enviado: {}", data.getId());
        } catch (Exception e) {
            log.warn("Erro ao enviar o e-mail", e);
            throw new RuntimeException("Falha ao enviar o e-mail");
        }
    }
/*
    public void enviarEmail(String destinatario, String assunto, String html) {
        EmailMessage mensagem = new EmailMessage()
                .setSenderAddress(senderEmail)
                .setToRecipients(destinatario)
                .setSubject(assunto)
                .setBodyHtml(html);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(mensagem);
        PollResponse<EmailSendResult> response = poller.waitForCompletion();
        log.info("Email enviado, ID da operação: " + response.getValue().getId());
    }
    */
}
