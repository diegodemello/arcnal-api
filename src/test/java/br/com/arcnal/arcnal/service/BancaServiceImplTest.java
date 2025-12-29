package br.com.arcnal.arcnal.service;


import br.com.arcnal.arcnal.application.service.BancaServiceImpl;
import br.com.arcnal.arcnal.domain.repositories.BancaRepository;
import br.com.arcnal.arcnal.application.dto.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.exception.BancaExistenteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BancaServiceImplTest {

    @InjectMocks
    BancaServiceImpl bancaService;

    @Mock
    BancaRepository bancaRepository;

    @Test
    @DisplayName("Deve lançar exceção quando tentar adicionar uma banca que já existe")
    public void deveLancarExcecaoQuandoAdicionarBancaComNomeExistente(){
        BancaRequestDTO bancaRequest = new BancaRequestDTO("Escola XYZ");
        when(bancaRepository.existsByNome(bancaRequest.nome()))
                .thenReturn(true);
        assertThrows(BancaExistenteException.class, () -> {
            bancaService.adicionarBanca(bancaRequest);
        });
    }
}