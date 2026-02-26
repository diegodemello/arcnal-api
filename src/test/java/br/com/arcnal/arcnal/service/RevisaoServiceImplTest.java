package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.application.dto.request.AdicionarQuestaoRevisaoRequestDTO;
import br.com.arcnal.arcnal.application.mapper.RevisaoMapper;
import br.com.arcnal.arcnal.application.service.impl.RevisaoServiceImpl;
import br.com.arcnal.arcnal.domain.entities.Questao;
import br.com.arcnal.arcnal.domain.entities.Revisao;
import br.com.arcnal.arcnal.domain.repositories.QuestaoRepository;
import br.com.arcnal.arcnal.domain.repositories.RevisaoRepository;
import br.com.arcnal.arcnal.domain.repositories.UsuarioRepository;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.application.dto.request.RevisaoRequestDTO;
import br.com.arcnal.arcnal.domain.exception.QuestaoNaoEncontradaException;
import br.com.arcnal.arcnal.domain.exception.RevisoesExistentesException;
import br.com.arcnal.arcnal.domain.exception.UsuarioNaoEncontradoException;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class RevisaoServiceImplTest {
    @InjectMocks
    RevisaoServiceImpl revisaoService;

    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    QuestaoRepository questaoRepository;
    @Mock
    RevisaoRepository revisaoRepository;
    @Mock
    RevisaoMapper revisaoMapper;
    @Mock
    Counter revisoesCriadas;

    RevisaoRequestDTO request;
    String idUsuario= "e486086c-0b6b-47df-9d69-2ae1f3097563";
    List<Integer> idQuestoes = Arrays.asList(1, 2, 3, 4, 5);
    String emailUsuario = "diego@arcnal.com.br";

    @BeforeEach
    public void setUp() {
        request = new RevisaoRequestDTO(
                "Revisão do Diego",
                idQuestoes
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário inexistente for consultado")
    public void deveRetornarUsuarioNaoEncontradoExceptionQuandoUsuarioInexistente() {
        when(usuarioRepository.findByEmailEndereco(emailUsuario))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> {
           revisaoService.criarRevisao(request, emailUsuario);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando questão inexistente for consultada")
    public void deveRetornarQuestaoNaoEncontradaExceptionQuandoQuestaoInexistente() {
        when(usuarioRepository.findByEmailEndereco(emailUsuario))
                .thenReturn(Optional.of(new Usuario()));
        when(questaoRepository.findAllById(request.idQuestoes()))
                .thenReturn(Collections.emptyList());
        Assertions.assertThrows(QuestaoNaoEncontradaException.class, () -> {
           revisaoService.criarRevisao(request, emailUsuario);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário inexistente for consultado")
    public void deveRetornarUsuarioNaoEncontradoExceptionQuandoUsuarioInexistenteNoListar() {
        when(usuarioRepository.findById(UUID.fromString(idUsuario)))
                        .thenReturn(Optional.empty());
        Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> {
            revisaoService.listarRevisoesPorUsuario(UUID.fromString(idUsuario));
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver revisões para o usuário")
    public void deveRetornarRevisoesExistentesExceptionQuandoNaoHouverRevisoes() {
        when(usuarioRepository.findById(UUID.fromString(idUsuario)))
                .thenReturn(Optional.of(new Usuario()));
        when(revisaoRepository.findAllByUsuarioId(UUID.fromString(idUsuario)))
                .thenReturn(Collections.emptyList());
        Assertions.assertThrows(RevisoesExistentesException.class, () -> {
           revisaoService.listarRevisoesPorUsuario(UUID.fromString(idUsuario));
        });
    }

    @Test
    @DisplayName("Deve criar revisão com sucesso")
    public void deveCriarRevisaoComSucesso() {
        Usuario usuario = new Usuario();
        RevisaoRequestDTO dto = new RevisaoRequestDTO("Revisão de Teste",
                Arrays.asList(1, 2, 3));
        List<Questao> questoes = Arrays.asList(new Questao(), new Questao(), new Questao());
        Revisao revisao = new Revisao();

        when(usuarioRepository.findByEmailEndereco("test@gmail.com"))
                .thenReturn(Optional.of(usuario));
        when(questaoRepository.findAllById(dto.idQuestoes()))
                .thenReturn(questoes);
        when(revisaoMapper.toEntity(dto))
                .thenReturn(revisao);
        when(revisaoRepository.save(revisao))
                .thenReturn(revisao);

        revisaoService.criarRevisao(dto, "test@gmail.com");
        verify(usuarioRepository).findByEmailEndereco("test@gmail.com");
        verify(questaoRepository).findAllById(dto.idQuestoes());
        verify(revisaoMapper).toEntity(dto);
        verify(revisaoRepository).save(any(Revisao.class));
    }

    @Test
    @DisplayName("Deve adicionar questões a revisão com sucesso")
    public void deveAdicionarQuestoesARevisaoComSucesso() {
        List<Questao> questoes = Arrays.asList(new Questao(), new Questao());
        Revisao revisao = new Revisao();
        revisao.setQuestoes(new ArrayList<>());
        AdicionarQuestaoRevisaoRequestDTO dto = new AdicionarQuestaoRevisaoRequestDTO(
                Arrays.asList(1, 2)
        );
        UUID idRevisao = UUID.randomUUID();
        List<Integer> idQuestoes = Arrays.asList(1, 2);
        when(questaoRepository.findAllById(idQuestoes))
                .thenReturn(questoes);
        when(revisaoRepository.findById(idRevisao))
                .thenReturn(Optional.of(revisao));
        when(revisaoRepository.save(revisao))
                .thenReturn(revisao);

        revisaoService.adicionarQuestao(idRevisao, dto);
    }
}