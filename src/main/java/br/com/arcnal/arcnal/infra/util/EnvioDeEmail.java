package br.com.arcnal.arcnal.infra.util;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnvioDeEmail {
    @Value("${azure.communication.services.connection-string}")
    String connectionString;

    @Value("${azure.communication.services.sender-email}")
    String senderEmail;

    private EmailClient emailClient;

    @PostConstruct
    public void init(){
        this.emailClient = new EmailClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    public void enviarEmail(String destinatario, String assunto, String corpo) {
        EmailMessage mensagem = new EmailMessage()
                .setSenderAddress(senderEmail)
                .setToRecipients(destinatario)
                .setSubject(assunto)
                .setBodyPlainText(corpo);

        SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(mensagem);
        PollResponse<EmailSendResult> response = poller.waitForCompletion();
        log.info("Email enviado, ID da operação: " + response.getValue().getId());
    }
}
