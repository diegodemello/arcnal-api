package br.com.arcnal.arcnal.service;


import br.com.arcnal.arcnal.application.mapper.BancaMapper;
import br.com.arcnal.arcnal.application.service.impl.BancaServiceImpl;
import br.com.arcnal.arcnal.domain.entities.Banca;
import br.com.arcnal.arcnal.domain.repositories.BancaRepository;
import br.com.arcnal.arcnal.application.dto.request.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.exception.BancaExistenteException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BancaServiceImplTest {

    @InjectMocks
    BancaServiceImpl bancaService;

    @Mock
    BancaRepository bancaRepository;
    @Mock
    BancaMapper bancaMapper;

    @Mock
    private Counter bancasCriadas;

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

    @Test
    @DisplayName("Deve adicionar banca quando não existente")
    public void deveAdicionarBancaQuandoNaoExistente(){
        BancaRequestDTO dto = new BancaRequestDTO("Banca");
        Banca banca = new Banca(1, new Nome("Banca"));

        when(bancaRepository.existsByNome("Banca"))
                .thenReturn(false);
        when(bancaMapper.toEntity(dto))
                .thenReturn(banca);
        when(bancaRepository.save(banca))
                .thenReturn(banca);

        Banca resultado = bancaService.adicionarBanca(dto);

        assertEquals(banca, resultado);

        verify(bancaRepository).existsByNome("Banca");
        verify(bancaMapper).toEntity(dto);
        verify(bancaRepository).save(banca);
    }

    @Test
    @DisplayName("Deve retornar todas as bancas existentes")
    public void deveRetornarTodasAsBancasExistentes(){
        List<Banca> bancas = List.of(
                new Banca(1, new Nome("Banca 1")),
                new Banca(2, new Nome("Banca 2"))
        );

        when(bancaRepository.findAll())
                .thenReturn(bancas);

        List<Banca> resultado = bancaService.listarTodasBancas();

        assertEquals(bancas, resultado);
        verify(bancaRepository).findAll();
    }
}