package br.com.arcnal.arcnal.service;


import br.com.arcnal.arcnal.dao.BancaDAO;
import br.com.arcnal.arcnal.dto.BancaRequestDTO;
import br.com.arcnal.arcnal.exception.BancaExistenteException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BancaServiceImplTest {

    @InjectMocks
    BancaServiceImpl bancaService;

    @Mock
    BancaDAO bancaDAO;

    @Test
    @DisplayName("Deve lançar exceção quando tentar adicionar uma banca que já existe")
    public void deveLancarExcecaoQuandoAdicionarBancaComNomeExistente(){
        BancaRequestDTO bancaRequest = new BancaRequestDTO("Escola XYZ");
        Mockito.when(bancaDAO.existsByNome(bancaRequest.nome()))
                .thenReturn(true);
        Assertions.assertThrows(BancaExistenteException.class, () -> {
            bancaService.adicionarBanca(bancaRequest);
        });
    }
}