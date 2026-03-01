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
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstatisticaUsuarioImpl implements IEstatisticaUsuarioService {

    private final EstatisticaUsuarioRepository repository;
    private final IRevisaoService revisaoService;
    private final AuthFacade authFacade;

    public EstatisticaUsuarioResponseDTO buscarEstatistica(){
        UUID usuarioId = authFacade.getUsuarioAutenticado().getId();
        DadosGeraisEstatisticaResponseDTO resumo = repository.buscarResumoGeral(usuarioId);
        List<DesempenhoResponseDTO> porMateria = repository.buscarDesempenhoPorMateria(usuarioId);
        List<DesempenhoResponseDTO> porAssunto = repository.buscarDesempenhoPorAssunto(usuarioId);
        Long revisoes = revisaoService.quantidadeDeRevisoesPorUsuario(usuarioId);
        double taxa = resumo.totalRespondidas() == 0
                ? 0
                : (resumo.totalAcertos() * 100.0) / resumo.totalRespondidas();

        return new EstatisticaUsuarioResponseDTO(
                resumo.totalRespondidas(),
                taxa,
                revisoes,
                resumo.materiasEstudadas(),
                porMateria,
                porAssunto
        );
    }
}
