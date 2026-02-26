package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.application.mapper.MateriaMapper;
import br.com.arcnal.arcnal.application.service.impl.MateriaServiceImpl;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.repositories.MateriaRepository;
import br.com.arcnal.arcnal.application.dto.request.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.exception.MateriaExistenteException;
import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MateriaServiceImplTest {

    @InjectMocks
    MateriaServiceImpl materiaService;

    @Mock
    MateriaRepository materiaRepository;
    @Mock
    MateriaMapper materiaMapper;
    @Mock
    Counter materiasCriadas;

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar matéria com nome já existente")
    public void deveRetornarExcecaoQuandoCriarMateriaComNomeExistente() {
        MateriaRequestDTO request = new MateriaRequestDTO("Matemática");
        when(materiaRepository.existsByNome(request.nome()))
                .thenReturn(true);

        assertThrows(MateriaExistenteException.class, () -> {
            materiaService.criarMateriaSemAssuntos(request);
        });
    }

    @Test
    @DisplayName("Deve criar matéria com nome único")
    public void deveCriarMateriaComNomeUnico() {
        MateriaRequestDTO dto = new MateriaRequestDTO("Matemática");
        Materia materia = new Materia(1, new Nome("Matemática"));
        when(materiaRepository.existsByNome(dto.nome()))
                .thenReturn(false);
        when(materiaMapper.toEntity(dto))
                .thenReturn(materia);
        when(materiaRepository.save(materia))
                .thenReturn(materia);

        Materia resultado = materiaService.criarMateriaSemAssuntos(dto);

        assertEquals(resultado, materia);
        verify(materiaRepository).save(materia);
    }

    @Test
    @DisplayName("Deve listar todas as matérias")
    public void deveListarTodasMaterias() {
        List<Materia> materias = List.of(
                new Materia(1, new Nome("Matemática")),
                new Materia(2, new Nome("Português"))
        );

        when(materiaRepository.findAll())
                .thenReturn(materias);
        List<Materia> resultado = materiaService.listarTodasMaterias();

        assertEquals(resultado, materias);
        verify(materiaRepository).findAll();
    }
}