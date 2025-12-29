package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.application.service.AssuntoServiceImpl;
import br.com.arcnal.arcnal.domain.repositories.MateriaRepository;
import br.com.arcnal.arcnal.application.dto.AssuntoRequestDTO;
import br.com.arcnal.arcnal.domain.exception.MateriaNaoEncontradaException;
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
class AssuntoServiceImplTest {

    @InjectMocks
    AssuntoServiceImpl assuntoService;

    @Mock
    MateriaRepository materiaRepository;

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

}