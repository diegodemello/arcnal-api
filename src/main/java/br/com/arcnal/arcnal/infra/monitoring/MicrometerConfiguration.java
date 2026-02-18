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
    @Qualifier("usuariosCadastrados")
    public Counter usuariosCadastrados(){
        return Counter
                .builder("usuarios.cadastrados")
                .description("Contador de usuários cadastrados")
                .register(meterRegistry);
    }

    @Bean
    @Qualifier("questoesCriadas")
    public Counter questoesCriadas(){
        return Counter
                .builder("questoes.criadas")
                .description("Contador de questões criadas")
                .register(meterRegistry);
    }

    @Bean
    @Qualifier("questoesRespondidas")
    public Counter questoesRespondidas(){
        return Counter
                .builder("questoesRespondidas")
                .description("Contador de questões respondidas")
                .register(meterRegistry);
    }

    @Bean
    @Qualifier("materiasCriadas")
    public Counter materiasCriadas(){
        return Counter
                .builder("materiasCriadas")
                .description("Contador de matérias criadas")
                .register(meterRegistry);
    }

    @Bean
    @Qualifier("bancasCriadas")
    public Counter bancasCriadas(){
        return Counter
                .builder("bancasCriadas")
                .description("Contador de bancas criadas")
                .register(meterRegistry);
    }

    @Bean
    @Qualifier("assuntosCriados")
    public Counter assuntosCriados(){
        return Counter
                .builder("assuntosCriados")
                .description("Contador de assuntos criados")
                .register(meterRegistry);
    }

    @Bean
    @Qualifier("revisoesCriadas")
    public Counter revisoesCriadas(){
        return Counter
                .builder("revisoesCriadas")
                .description("Contador de revisões criadas")
                .register(meterRegistry);
    }
}
