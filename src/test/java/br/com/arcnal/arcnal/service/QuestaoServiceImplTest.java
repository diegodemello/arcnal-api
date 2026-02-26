package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.application.dto.response.QuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.ResolucaoQuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.RespostaQuestaoResponseDTO;
import br.com.arcnal.arcnal.application.mapper.QuestaoMapper;
import br.com.arcnal.arcnal.application.service.impl.QuestaoServiceImpl;
import br.com.arcnal.arcnal.domain.entities.*;
import br.com.arcnal.arcnal.domain.repositories.*;
import br.com.arcnal.arcnal.domain.enums.Nivel;
import br.com.arcnal.arcnal.application.dto.request.QuestaoRequestDTO;
import br.com.arcnal.arcnal.domain.exception.AlternativaInvalidaException;
import br.com.arcnal.arcnal.domain.exception.AnoInvalidoException;
import br.com.arcnal.arcnal.domain.exception.AssuntoNaoEncontradoException;
import br.com.arcnal.arcnal.domain.exception.BancaExistenteException;
import br.com.arcnal.arcnal.domain.exception.EnunciadoExistenteException;
import br.com.arcnal.arcnal.domain.exception.MateriaNaoEncontradaException;
import br.com.arcnal.arcnal.domain.valueobjects.Alternativas;
import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import br.com.arcnal.arcnal.infra.util.AuthFacade;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class QuestaoServiceImplTest {

    @InjectMocks
    QuestaoServiceImpl questaoService;

    @Mock
    QuestaoRepository questaoRepository;
    @Mock
    BancaRepository bancaRepository;
    @Mock
    MateriaRepository materiaRepository;
    @Mock
    AssuntoRepository assuntoRepository;
    @Mock
    RespostaUsuarioRepository respostaUsuarioRepository;
    @Mock
    QuestaoMapper questaoMapper;
    @Mock
    Counter questoesCriadas;
    @Mock
    AuthFacade authFacade;

    QuestaoRequestDTO request;

    @BeforeEach
    public void setUp() {
        request = new QuestaoRequestDTO(1, 2, 1, 2010,
                "Qual a capital da França?", null, Nivel.MEDIO, "Berlim",
                "Madrid", "Paris", "Roma",
                "Lisboa", 'A', "A capital da França é Paris.", "http://video.com/correcao");
    }

    @Test
    @DisplayName("Deve retornar exceção quando enunciado for repetido")
    public void deveRetornarExcecaoQuandoEnunciadoForRepetido() {
        when(questaoRepository.existsByMetadadosEnunciado(request.enunciado()))
                .thenReturn(true);
        assertThrows(EnunciadoExistenteException.class, () -> {
            questaoService.adicionarQuestao(request);
        });
    }

    @Test
    @DisplayName("Deve retornar exceção quando ano for inválido")
    public void deveRetornarExcecaoQuandoAnoForInvalido() {
        QuestaoRequestDTO requestAnoInvalido = new QuestaoRequestDTO(1, 2, 1, 2050,
                "Qual a capital da França?", null, Nivel.MEDIO, "Berlim",
                "Madrid", "Paris", "Roma",
                "Lisboa", 'A', "A capital da França é Paris.", "http://video.com/correcao");

        when(questaoRepository.existsByMetadadosEnunciado(request.enunciado()))
                .thenReturn(false);

        assertThrows(AnoInvalidoException.class, () -> {
            questaoService.adicionarQuestao(requestAnoInvalido);
        });
    }

    @Test
    @DisplayName("Deve retornar exceção quando alternativa correta for inválida")
    public void deveRetornarExcecaoQuandoAlternativaCorretaForInvalida() {
        QuestaoRequestDTO requestAlternativaInvalida = new QuestaoRequestDTO(1, 2, 1, 2010,
                "Qual a capital da França?", null, Nivel.MEDIO, "Berlim",
                "Madrid", "Paris", "Roma",
                "Lisboa", 'F', "A capital da França é Paris.", "http://video.com/correcao");
        assertThrows(AlternativaInvalidaException.class, () -> {
            questaoService.adicionarQuestao(requestAlternativaInvalida);
        });
    }

    @Test
    @DisplayName("Deve retornar BancaExistenteException quando não encontrar banca")
    public void deveRetornarBancaExistenteExceptionQuandoNaoEncontrarBanca() {
        when(bancaRepository.findById(request.idBanca()))
                .thenReturn(Optional.empty());
        assertThrows(BancaExistenteException.class, () -> {
            questaoService.adicionarQuestao(request);
        });
    }

    @Test
    @DisplayName("Deve retornar MateriaNaoEncontradaException quando não encontrar matéria")
    public void deveRetornarMateriaNaoEncontradaExceptionQuandoNaoEncontrarMateria() {
        when(bancaRepository.findById(request.idBanca()))
                        .thenReturn(Optional.of(new Banca()));
        when(materiaRepository.findById(request.idMateria()))
                .thenReturn(Optional.empty());
        assertThrows(MateriaNaoEncontradaException.class, () -> {
            questaoService.adicionarQuestao(request);
        });
    }

    @Test
    @DisplayName("Deve retornar AssuntoNaoEncontradoException quando não encontrar assunto")
    public void deveRetornarAssuntoNaoEncontradoExceptionQuandoNaoEncontrarAssunto() {
        when(bancaRepository.findById(request.idBanca()))
                .thenReturn(Optional.of(new Banca()));
        when(materiaRepository.findById(request.idMateria()))
                .thenReturn(Optional.of(new Materia()));
        when(assuntoRepository.findById(request.idAssunto()))
                .thenReturn(Optional.empty());

        assertThrows(AssuntoNaoEncontradoException.class, () -> {
            questaoService.adicionarQuestao(request);
        });
    }

    @Test
    @DisplayName("Deve adicionar questão com sucesso")
    public void deveAdicionarQuestaoComSucesso() {
        Banca banca = new Banca(1, new Nome("EsPCEx"));
        Materia materia = new Materia(1, new Nome("Matemática"));
        Assunto assunto = new Assunto(1, materia, new Nome("Geometria"));
        Questao questao = new Questao();
        QuestaoResponseDTO response = new QuestaoResponseDTO(
                1,
                banca.getNome(),
                materia.getNome(),
                assunto.getNome(),
                null,
                Nivel.MEDIO,
                2010,
                "Qual a capital da França?",
                "Berlim",
                "Madrid",
                "Paris",
                "Roma",
                "Lisboa"
        );

        when(bancaRepository.findById(1))
                .thenReturn(Optional.of(banca));
        when(materiaRepository.findById(2))
                .thenReturn(Optional.of(materia));
        when(assuntoRepository.findById(1))
                .thenReturn(Optional.of(assunto));

        when(questaoMapper.toEntity(request))
                .thenReturn(questao);

        when(questaoRepository.save(questao))
                .thenReturn(questao);
        when(questaoMapper.toResponse(questao))
                .thenReturn(response);

        QuestaoResponseDTO resposta = questaoService.adicionarQuestao(request);
        assertEquals(resposta, response);
    }

    @Test
    @DisplayName("Deve listar questões com sucesso")
    public void deveListarQuestoesComSucesso() {
        List<Questao> questoes = List.of(new Questao(), new Questao());
        List<QuestaoResponseDTO> dtos = List.of(
                new QuestaoResponseDTO(1, "EsPCEx", "Matemática", "Geometria", null, Nivel.MEDIO, 2010,
                "Qual a capital da França?", "Berlim", "Madrid", "Paris", "Roma", "Lisboa"),
                new QuestaoResponseDTO(2, "EsPCEx", "Matemática", "Geometria", null, Nivel.MEDIO, 2010,
                        "Qual a capital da França?", "Berlim", "Madrid", "Paris", "Roma", "Lisboa")
        );

        when(questaoRepository.findAll(PageRequest.of(0, 2)))
                .thenReturn(new PageImpl<>(questoes));
        when(questaoMapper.toResponses(questoes))
                .thenReturn(dtos);

        List<QuestaoResponseDTO> resultado = questaoService.listarQuestoes(0, 2);
        assertEquals(resultado, dtos);
    }

    @Test
    @DisplayName("Deve listar questões por filtro com sucesso")
    public void deveListarQuestoesPorFiltroComSucesso() {
        Integer pagina = 0;
        Integer objetos = 2;
        Integer idBanca = 1;
        Integer ano = 2010;
        Integer idMateria = 2;
        Integer idAssunto = 1;

        Banca banca = new Banca(1, new Nome("EsPCEx"));
        Materia materia = new Materia(2, new Nome("Matemática"));
        Assunto assunto = new Assunto(1, materia, new Nome("Geometria"));

        List<Questao> questoes = List.of(new Questao(), new Questao());
        List<QuestaoResponseDTO> dtos = List.of(
                new QuestaoResponseDTO(1, "EsPCEx", "Matemática", "Geometria", null, Nivel.MEDIO, 2010,
                        "Qual a capital da França?", "Berlim", "Madrid", "Paris", "Roma", "Lisboa"),
                new QuestaoResponseDTO(2, "EsPCEx", "Matemática", "Geometria", null, Nivel.MEDIO, 2010,
                        "Qual a capital da França?", "Berlim", "Madrid", "Paris", "Roma", "Lisboa")
        );

        Pageable pageable = PageRequest.of(pagina, objetos);

        when(bancaRepository.findById(idBanca))
                .thenReturn(Optional.of(banca));
        when(materiaRepository.findById(idMateria))
                .thenReturn(Optional.of(materia));
        when(assuntoRepository.findById(idAssunto))
                .thenReturn(Optional.of(assunto));
        when(questaoRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(questoes));
        when(questaoMapper.toResponses(questoes)).thenReturn(dtos);

        List<QuestaoResponseDTO> resultado = questaoService.listarQuestoesPorFiltro(pagina, objetos, idBanca, ano, idMateria, idAssunto);

        assertEquals(dtos, resultado);
    }

    @Test
    @DisplayName("Deve listar questões por filtro com parâmetros nulos")
    public void deveListarQuestoesPorFiltroComParametrosNulos() {
        List<Questao> questoes = List.of(new Questao(), new Questao());
        List<QuestaoResponseDTO> dtos = List.of(
                new QuestaoResponseDTO(1, "EsPCEx", "Matemática", "Geometria", null, Nivel.MEDIO, 2010,
                        "Questão 1", "A", "B", "C", "D", "E"),
                new QuestaoResponseDTO(2, "EsPCEx", "Matemática", "Geometria", null, Nivel.MEDIO, 2010,
                        "Questão 2", "A", "B", "C", "D", "E")
        );

        Pageable pageable = PageRequest.of(0, 2);

        when(questaoRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(new PageImpl<>(questoes));
        when(questaoMapper.toResponses(questoes))
                .thenReturn(dtos);

        List<QuestaoResponseDTO> resultado = questaoService.listarQuestoesPorFiltro(0, 2, null, null, null, null);

        assertEquals(dtos, resultado);
    }

    @Test
    @DisplayName("Deve responder questão com sucesso")
    public void deveResponderQuestaoComSucesso() {
        Alternativas alternativas = new Alternativas("A", "B", "C", "D", "E", 'E');

        Questao questao = new Questao();
        questao.setId(1);
        questao.setAlternativas(alternativas);

        Usuario usuario = new Usuario();

        when(questaoRepository.findById(1))
                .thenReturn(Optional.of(questao));
        when(authFacade.getUsuarioAutenticado())
                .thenReturn(usuario);
        when(respostaUsuarioRepository.save(any(RespostaUsuario.class)))
                .thenReturn(new RespostaUsuario());

        RespostaQuestaoResponseDTO resultado = questaoService.responderQuestao(1, 'E');

        assertEquals(1, resultado.idQuestao());
        assertEquals('E', resultado.alternativaEscolhida());
        assertEquals(true, resultado.acertou());
        assertEquals('E', resultado.alternativaCorreta());
        verify(respostaUsuarioRepository, times(1)).save(any(RespostaUsuario.class));
    }

    @Test
    @DisplayName("Deve obter resolução da questão com sucesso")
    public void deveObterResolucaoQuestaoComSucesso() {
        Questao questao = new Questao();
        ResolucaoQuestaoResponseDTO dto = new ResolucaoQuestaoResponseDTO(1,
                'A', "É assim que faz.", "http://video.com/correcao");

        when(questaoRepository.findById(1))
                .thenReturn(Optional.of(questao));
        when(questaoMapper.toResolucaoResponse(questao))
                .thenReturn(dto);

        ResolucaoQuestaoResponseDTO resultado = questaoService.obterResolucaoQuestao(1);
        assertEquals(resultado, dto);
    }
}