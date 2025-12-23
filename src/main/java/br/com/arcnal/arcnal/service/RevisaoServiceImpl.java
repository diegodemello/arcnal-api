package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dao.QuestaoDAO;
import br.com.arcnal.arcnal.dao.RevisaoDAO;
import br.com.arcnal.arcnal.dao.UsuarioDAO;
import br.com.arcnal.arcnal.dto.RevisaoRequestDTO;
import br.com.arcnal.arcnal.dto.RevisaoResponseDTO;
import br.com.arcnal.arcnal.domain.Questao;
import br.com.arcnal.arcnal.domain.Revisao;
import br.com.arcnal.arcnal.domain.Usuario;
import br.com.arcnal.arcnal.exception.QuestaoNaoEncontradaException;
import br.com.arcnal.arcnal.exception.RevisoesExistentesException;
import br.com.arcnal.arcnal.exception.UsuarioNaoEncontradoException;
import br.com.arcnal.arcnal.mapper.RevisaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RevisaoServiceImpl implements IRevisaoService {

    private final RevisaoDAO revisaoDAO;
    private final RevisaoMapper revisaoMapper;
    private final UsuarioDAO usuarioDAO;
    private final QuestaoDAO questaoDAO;

    @Override
    public void criarRevisao(RevisaoRequestDTO dto, String email) {

        Usuario usuario = buscarUsuarioPorEmail(email);
        List<Questao> questoes = buscarEValidarQuestoes(dto.idQuestoes());

        Revisao revisao = revisaoMapper.toEntity(dto);
        revisao.setUsuario(usuario);
        revisao.setQuestoes(questoes);

        revisaoDAO.save(revisao);
    }

    @Override
    public List<RevisaoResponseDTO> listarRevisoesPorUsuario(UUID idUsuario) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        List<Revisao> revisoes = buscarRevisoesPorUsuario(idUsuario);

        validarRevisoesEncontradas(revisoes);

        return revisaoMapper.toResponse(revisoes);
    }

    private Usuario buscarUsuarioPorEmail(String email){
        return usuarioDAO.findAllByEmail(email);
    }

    private Usuario buscarUsuarioPorId(UUID id){
        return usuarioDAO.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    }

    private List<Revisao> buscarRevisoesPorUsuario(UUID id){
        return revisaoDAO.findAllByUsuarioId(id);
    }

    private List<Questao> buscarEValidarQuestoes(List<Integer> idQuestoes) {
        List<Questao> questoes = questaoDAO.findAllById(idQuestoes);
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
