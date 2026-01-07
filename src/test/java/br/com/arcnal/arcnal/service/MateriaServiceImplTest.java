package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.application.service.impl.MateriaServiceImpl;
import br.com.arcnal.arcnal.domain.repositories.MateriaRepository;
import br.com.arcnal.arcnal.application.dto.request.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.exception.MateriaExistenteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MateriaServiceImplTest {

    @InjectMocks
    MateriaServiceImpl materiaService;

    @Mock
    MateriaRepository materiaRepository;

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
}