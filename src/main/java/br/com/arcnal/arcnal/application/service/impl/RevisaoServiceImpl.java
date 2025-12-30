package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.service.IRevisaoService;
import br.com.arcnal.arcnal.domain.repositories.QuestaoRepository;
import br.com.arcnal.arcnal.domain.repositories.RevisaoRepository;
import br.com.arcnal.arcnal.domain.repositories.UsuarioRepository;
import br.com.arcnal.arcnal.application.dto.request.RevisaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.RevisaoResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Questao;
import br.com.arcnal.arcnal.domain.entities.Revisao;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.domain.exception.QuestaoNaoEncontradaException;
import br.com.arcnal.arcnal.domain.exception.RevisoesExistentesException;
import br.com.arcnal.arcnal.domain.exception.UsuarioNaoEncontradoException;
import br.com.arcnal.arcnal.application.mapper.RevisaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RevisaoServiceImpl implements IRevisaoService {

    private final RevisaoRepository revisaoRepository;
    private final RevisaoMapper revisaoMapper;
    private final UsuarioRepository usuarioRepository;
    private final QuestaoRepository questaoRepository;

    @Override
    public void criarRevisao(RevisaoRequestDTO dto, String email) {

        Usuario usuario = buscarUsuarioPorEmail(email);
        List<Questao> questoes = buscarEValidarQuestoes(dto.idQuestoes());

        Revisao revisao = revisaoMapper.toEntity(dto);
        revisao.setUsuario(usuario);
        revisao.setQuestoes(questoes);

        revisaoRepository.save(revisao);
    }

    @Override
    public List<RevisaoResponseDTO> listarRevisoesPorUsuario(UUID idUsuario) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        List<Revisao> revisoes = buscarRevisoesPorUsuario(idUsuario);

        validarRevisoesEncontradas(revisoes);

        return revisaoMapper.toResponse(revisoes);
    }

    private Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findAllByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o Email: " + email));
    }

    private Usuario buscarUsuarioPorId(UUID id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    }

    private List<Revisao> buscarRevisoesPorUsuario(UUID id){
        return revisaoRepository.findAllByUsuarioId(id);
    }

    private List<Questao> buscarEValidarQuestoes(List<Integer> idQuestoes) {
        List<Questao> questoes = questaoRepository.findAllById(idQuestoes);
        if(questoes.size() != idQuestoes.size()) {
            throw new QuestaoNaoEncontradaException("Uma ou mais questões não foram encontradas.");
        }
        return questoes;
    }

    private void validarRevisoesEncontradas(List<Revisao> revisoes){
        if(revisoes.isEmpty()){
            throw new RevisoesExistentesException("Nenhuma revisão encontrada para o usuário.");
        }
    }
}
