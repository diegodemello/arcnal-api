package br.com.arcnal.arcnal.infra.listener;

import br.com.arcnal.arcnal.domain.entities.EstatisticaUsuario;
import br.com.arcnal.arcnal.domain.event.QuestaoRespondidaEvent;
import br.com.arcnal.arcnal.domain.repositories.EstatisticaUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class EstatisticaListener {
    private final EstatisticaUsuarioRepository estatisticaUsuarioRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void aoResponderQuestao(QuestaoRespondidaEvent event){
        EstatisticaUsuario estatisticaUsuario = estatisticaUsuarioRepository.findByUsuarioAndMateriaAndAssunto(
                event.getUsuario(),
                event.getMateria(),
                event.getAssunto()
        ).orElseGet(() -> {
            EstatisticaUsuario novaEstatistica = new EstatisticaUsuario();
            novaEstatistica.setUsuario(event.getUsuario());
            novaEstatistica.setMateria(event.getMateria());
            novaEstatistica.setAssunto(event.getAssunto());
            novaEstatistica.setTotalRespondidas(0);
            novaEstatistica.setTotalAcertos(0);
            novaEstatistica.setTotalErros(0);
            return novaEstatistica;
        });

        estatisticaUsuario.setTotalRespondidas(estatisticaUsuario.getTotalRespondidas() + 1);
        if (event.isAcertou()){
            estatisticaUsuario.setTotalAcertos(estatisticaUsuario.getTotalAcertos() + 1);
        } else{
            estatisticaUsuario.setTotalErros(estatisticaUsuario.getTotalErros() + 1);
        }
        estatisticaUsuario.setUltimaAtualizacao(LocalDateTime.now());
        estatisticaUsuarioRepository.save(estatisticaUsuario);
    }
}
