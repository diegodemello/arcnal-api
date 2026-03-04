package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.dto.request.TemaRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.TemaResponseDTO;
import br.com.arcnal.arcnal.application.mapper.TemaMapper;
import br.com.arcnal.arcnal.application.service.ITemaService;
import br.com.arcnal.arcnal.domain.entities.Tema;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.domain.repositories.TemaRepository;
import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import br.com.arcnal.arcnal.infra.util.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemaServiceImpl implements ITemaService {

    private final TemaRepository repository;
    private final TemaMapper mapper;
    private final AuthFacade authFacade;

    @Override
    public void criarTema(TemaRequestDTO request) {
        Usuario usuario = authFacade.getUsuarioAutenticado();
        Tema tema = new Tema();
        tema.setTitulo(new Nome(request.titulo()));
        tema.setTextoDeApoio(request.textoDeApoio());
        tema.setUsuario(usuario);
        tema.setCriadoEm(LocalDateTime.now());
        repository.save(tema);
    }

    @Override
    public List<TemaResponseDTO> listarTemas() {
        List<Tema> temas = repository.findAll();
        return mapper.toResponsesDTOs(temas);
    }

    @Override
    public void deletarTema(Integer idTema) {
        repository.deleteById(idTema);
    }
}
