package br.com.arcnal.arcnal.service;


import br.com.arcnal.arcnal.application.service.impl.UsuarioServiceImpl;
import br.com.arcnal.arcnal.domain.repositories.UsuarioRepository;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.domain.enums.Cargo;
import br.com.arcnal.arcnal.application.dto.request.UsuarioRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.UsuarioResponseDTO;
import br.com.arcnal.arcnal.domain.exception.EmailEmUsoException;
import br.com.arcnal.arcnal.application.mapper.UsuarioMapper;
import br.com.arcnal.arcnal.domain.valueobjects.Email;
import br.com.arcnal.arcnal.domain.valueobjects.NomeUsuario;
import br.com.arcnal.arcnal.domain.valueobjects.Senha;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    Counter usuariosCadastrados;

    private UsuarioRequestDTO dto;
    private UsuarioResponseDTO usuarioResponseDTO;

    @BeforeEach
    public void setUp(){
        dto = new UsuarioRequestDTO("Diego de Mello", "diegodemello027@gmail.com", "Diego123");
        String uuidString = "43b66b34-db63-4239-b756-df91e965781d";
        usuarioResponseDTO = new UsuarioResponseDTO(
                UUID.fromString(uuidString),
                "Diego de Mello",
                "diego@gmail.com",
                false,
                Cargo.USUARIO);
    }

    @Test
    @DisplayName("Deve retornar EmailEmUsoException quando cadastrar usuário com email já existente")
    public void deveRetornarEmailEmUsoExceptionQuandoCadastrarUsuarioComEmailJaExistente() {
        when(usuarioRepository.existsByEmail(dto.email()))
                .thenReturn(true);
        assertThrows(EmailEmUsoException.class, () -> {
            usuarioService.cadastrarUsuario(dto);
        });
    }

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso")
    public void deveCadastrarUsuarioComSucesso() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Teste da Silva", "test@gmail.com", "Teste123#");
        Usuario usuario = new Usuario(UUID.randomUUID(),
                new NomeUsuario("Teste da Silva"),
                new Email("test@gmail.com"),
                new Senha("Teste123#"),
                Cargo.USUARIO,
                false,
                null,
                null
        );
        when(usuarioRepository.existsByEmail("test@gmail.com"))
                .thenReturn(false);
        when(usuarioMapper.toEntity(dto))
                .thenReturn(usuario);
        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);

        usuarioService.cadastrarUsuario(dto);
    }
}