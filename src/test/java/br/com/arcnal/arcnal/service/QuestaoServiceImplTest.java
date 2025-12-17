package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dao.AssuntoDAO;
import br.com.arcnal.arcnal.dao.BancaDAO;
import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dao.QuestaoDAO;
import br.com.arcnal.arcnal.domain.Banca;
import br.com.arcnal.arcnal.domain.Materia;
import br.com.arcnal.arcnal.domain.enums.Nivel;
import br.com.arcnal.arcnal.dto.QuestaoRequestDTO;
import br.com.arcnal.arcnal.exception.AlternativaInvalidaException;
import br.com.arcnal.arcnal.exception.AnoInvalidoException;
import br.com.arcnal.arcnal.exception.AssuntoNaoEncontradoException;
import br.com.arcnal.arcnal.exception.BancaExistenteException;
import br.com.arcnal.arcnal.exception.EnunciadoExistenteException;
import br.com.arcnal.arcnal.exception.MateriaNaoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class QuestaoServiceImplTest {

    @InjectMocks
    QuestaoServiceImpl questaoService;

    @Mock
    QuestaoDAO questaoDAO;
    @Mock
    BancaDAO bancaDAO;
    @Mock
    MateriaDAO materiaDAO;
    @Mock
    AssuntoDAO assuntoDAO;

    QuestaoRequestDTO request;

    @BeforeEach
    public void setUp() {
        request = new QuestaoRequestDTO(1, 2, 1, 2010,
                "Qual a capital da França?", Nivel.MEDIO, "Berlim",
                "Madrid", "Paris", "Roma",
                "Lisboa", 'A', "A capital da França é Paris.", "http://video.com/correcao");
    }

    @Test
    @DisplayName("Deve retornar exceção quando enunciado for repetido")
    public void deveRetornarExcecaoQuandoEnunciadoForRepetido() {
        when(questaoDAO.existsByEnunciado(request.enunciado()))
                .thenReturn(true);
        assertThrows(EnunciadoExistenteException.class, () -> {
            questaoService.adicionarQuestao(request);
        });
    }

    @Test
    @DisplayName("Deve retornar exceção quando ano for inválido")
    public void deveRetornarExcecaoQuandoAnoForInvalido() {
        QuestaoRequestDTO requestAnoInvalido = new QuestaoRequestDTO(1, 2, 1, 2050,
                "Qual a capital da França?", Nivel.MEDIO, "Berlim",
                "Madrid", "Paris", "Roma",
                "Lisboa", 'A', "A capital da França é Paris.", "http://video.com/correcao");

        when(questaoDAO.existsByEnunciado(request.enunciado()))
                .thenReturn(false);

        assertThrows(AnoInvalidoException.class, () -> {
            questaoService.adicionarQuestao(requestAnoInvalido);
        });
    }

    @Test
    @DisplayName("Deve retornar exceção quando alternativa correta for inválida")
    public void deveRetornarExcecaoQuandoAlternativaCorretaForInvalida() {
        QuestaoRequestDTO requestAlternativaInvalida = new QuestaoRequestDTO(1, 2, 1, 2010,
                "Qual a capital da França?", Nivel.MEDIO, "Berlim",
                "Madrid", "Paris", "Roma",
                "Lisboa", 'F', "A capital da França é Paris.", "http://video.com/correcao");
        assertThrows(AlternativaInvalidaException.class, () -> {
            questaoService.adicionarQuestao(requestAlternativaInvalida);
        });
    }

    @Test
    @DisplayName("Deve retornar BancaExistenteException quando não encontrar banca")
    public void deveRetornarBancaExistenteExceptionQuandoNaoEncontrarBanca() {
        when(bancaDAO.findById(request.idBanca()))
                .thenReturn(Optional.empty());
        assertThrows(BancaExistenteException.class, () -> {
            questaoService.adicionarQuestao(request);
        });
    }

    @Test
    @DisplayName("Deve retornar MateriaNaoEncontradaException quando não encontrar matéria")
    public void deveRetornarMateriaNaoEncontradaExceptionQuandoNaoEncontrarMateria() {
        when(bancaDAO.findById(request.idBanca()))
                        .thenReturn(Optional.of(new Banca()));
        when(materiaDAO.findById(request.idMateria()))
                .thenReturn(Optional.empty());
        assertThrows(MateriaNaoEncontradaException.class, () -> {
            questaoService.adicionarQuestao(request);
        });
    }

    @Test
    @DisplayName("Deve retornar AssuntoNaoEncontradoException quando não encontrar assunto")
    public void deveRetornarAssuntoNaoEncontradoExceptionQuandoNaoEncontrarAssunto() {
        when(bancaDAO.findById(request.idBanca()))
                .thenReturn(Optional.of(new Banca()));
        when(materiaDAO.findById(request.idMateria()))
                .thenReturn(Optional.of(new Materia()));
        when(assuntoDAO.findById(request.idAssunto()))
                .thenReturn(Optional.empty());

        assertThrows(AssuntoNaoEncontradoException.class, () -> {
            questaoService.adicionarQuestao(request);
        });
    }
}