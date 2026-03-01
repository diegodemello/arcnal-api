package br.com.arcnal.arcnal.domain.event;

import br.com.arcnal.arcnal.domain.entities.Assunto;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestaoRespondidaEvent {
    private Usuario usuario;
    private Materia materia;
    private Assunto assunto;
    private boolean acertou;
}
