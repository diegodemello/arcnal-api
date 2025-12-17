package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dto.MateriaRequestDTO;
import br.com.arcnal.arcnal.exception.MateriaExistenteException;
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
    MateriaDAO materiaDAO;

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar matéria com nome já existente")
    public void deveRetornarExcecaoQuandoCriarMateriaComNomeExistente() {
        MateriaRequestDTO request = new MateriaRequestDTO("Matemática");
        when(materiaDAO.existsByNome(request.nome()))
                .thenReturn(true);

        assertThrows(MateriaExistenteException.class, () -> {
            materiaService.criarMateriaSemAssuntos(request);
        });
    }
}