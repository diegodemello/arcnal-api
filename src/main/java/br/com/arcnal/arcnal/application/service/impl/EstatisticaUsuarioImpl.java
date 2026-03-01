package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.dto.response.DadosGeraisEstatisticaResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.DesempenhoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.EstatisticaUsuarioResponseDTO;
import br.com.arcnal.arcnal.application.service.IEstatisticaUsuarioService;
import br.com.arcnal.arcnal.application.service.IRevisaoService;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.domain.repositories.EstatisticaUsuarioRepository;
import br.com.arcnal.arcnal.infra.util.AuthFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstatisticaUsuarioImpl implements IEstatisticaUsuarioService {

    private final EstatisticaUsuarioRepository repository;
    private final IRevisaoService revisaoService;
    private final AuthFacade authFacade;

    public EstatisticaUsuarioResponseDTO buscarEstatistica(){
        Usuario usuario = authFacade.getUsuarioAutenticado();
        DadosGeraisEstatisticaResponseDTO resumo = repository.buscarResumoGeral(usuario.getId());
        Long materias = repository.materiasEstudadas(usuario.getId());
        List<DesempenhoResponseDTO> porMateria = repository.buscarDesempenhoPorMateria(usuario.getId());
        List<DesempenhoResponseDTO> porAssunto = repository.buscarDesempenhoPorAssunto(usuario.getId());
        Long revisoes = revisaoService.listarRevisoesPorUsuario(usuario.getId()).stream().count();
        double taxa = resumo.totalRespondidas() == 0
                ? 0
                : (resumo.totalAcertos() * 100.0) / resumo.totalRespondidas();

        return new EstatisticaUsuarioResponseDTO(
                resumo.totalRespondidas(),
                taxa,
                revisoes,
                materias,
                porMateria,
                porAssunto
        );
    }
}
