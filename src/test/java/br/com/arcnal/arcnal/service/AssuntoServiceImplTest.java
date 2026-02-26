package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.application.dto.response.AssuntoResponseDTO;
import br.com.arcnal.arcnal.application.mapper.AssuntoMapper;
import br.com.arcnal.arcnal.application.service.impl.AssuntoServiceImpl;
import br.com.arcnal.arcnal.domain.entities.Assunto;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.repositories.AssuntoRepository;
import br.com.arcnal.arcnal.domain.repositories.MateriaRepository;
import br.com.arcnal.arcnal.application.dto.request.AssuntoRequestDTO;
import br.com.arcnal.arcnal.domain.exception.MateriaNaoEncontradaException;
import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssuntoServiceImplTest {

    @InjectMocks
    AssuntoServiceImpl assuntoService;

    @Mock
    AssuntoMapper assuntoMapper;
    @Mock
    AssuntoRepository assuntoRepository;
    @Mock
    MateriaRepository materiaRepository;
    @Mock
    Counter assuntosCriados;

    @Test
    @DisplayName("Deve criar um novo assunto com sucesso")
    public void deveCriarNovoAssuntoComSucesso() {
        AssuntoRequestDTO request = new AssuntoRequestDTO("Assunto Teste", 10);
        Materia materia = new Materia(10, new Nome("Matéria Teste"));
        Assunto assunto = new Assunto();

        when(materiaRepository.findById(10))
                .thenReturn(Optional.of(materia));
        when(assuntoMapper.toEntity(request))
                .thenReturn(assunto);
        when(assuntoRepository.save(assunto))
                .thenReturn(assunto);
            assuntoService.criarNovoAssunto(request);
    }

    @Test
    @DisplayName("Deve lançar MateriaNaoEncontradaException quando a matéria não existir")
    public void deveRetornarMateriaNaoEncontradaExceptionQuandoMateriaNaoExistir() {
        AssuntoRequestDTO request = new AssuntoRequestDTO("Assunto Teste", 10);
        when(materiaRepository.findById(request.idMateria()))
                .thenReturn(Optional.empty());
        assertThrows(MateriaNaoEncontradaException.class, () -> {
            assuntoService.criarNovoAssunto(request);
        });
    }

    @Test
    @DisplayName("Deve listar assuntos por matéria com sucesso")
    public void deveListarAssuntosPorMateriaComSucesso() {
        Materia materia = new Materia(10, new Nome("Matéria Teste"));
        Assunto assunto1 = new Assunto();
        Assunto assunto2 = new Assunto();
        List<Assunto> assuntos = List.of(assunto1, assunto2);

        AssuntoResponseDTO dto1 = new AssuntoResponseDTO(1, "Assunto 1");
        AssuntoResponseDTO dto2 = new AssuntoResponseDTO(2, "Assunto 2");

        when(materiaRepository.findById(10))
                .thenReturn(Optional.of(materia));
        when(assuntoRepository.findAllByMateria_Id(10))
                .thenReturn(assuntos);
        when(assuntoMapper.toResponse(assunto1))
                .thenReturn(dto1);
        when(assuntoMapper.toResponse(assunto2))
                .thenReturn(dto2);

        assuntoService.listarAssuntosPorMateria(10);
    }

}