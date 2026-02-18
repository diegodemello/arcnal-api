package br.com.arcnal.arcnal.infra.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MicrometerConfiguration {
    private final MeterRegistry meterRegistry;

    @Bean
    @Qualifier("questoesCriadas")
    public Counter questoesCriadas(){
        return Counter
                .builder("questoes.criadas")
                .description("Contador de questões criadas")
                .register(meterRegistry);
    }

    @Bean
    @Qualifier("usuariosCadastrados")
    public Counter usuariosCadastrados(){
        return Counter
                .builder("usuarios.cadastrados")
                .description("Contador de usuários cadastrados")
                .register(meterRegistry);
    }
}
