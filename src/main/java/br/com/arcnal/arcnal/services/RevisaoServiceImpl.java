package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.QuestaoDAO;
import br.com.arcnal.arcnal.dao.RevisaoDAO;
import br.com.arcnal.arcnal.dao.UsuarioDAO;
import br.com.arcnal.arcnal.dtos.RevisaoRequestDTO;
import br.com.arcnal.arcnal.dtos.RevisaoResponseDTO;
import br.com.arcnal.arcnal.entities.Questao;
import br.com.arcnal.arcnal.entities.Revisao;
import br.com.arcnal.arcnal.entities.Usuario;
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
    public void criarRevisao(RevisaoRequestDTO dto) {
        Usuario usuario = buscarUsuarioPorId(dto.idUsuario());
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

    private Usuario buscarUsuarioPorId(UUID id){
        return usuarioDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private List<Revisao> buscarRevisoesPorUsuario(UUID id){
        return revisaoDAO.findAllByUsuarioId(id);
    }

    private List<Questao> buscarEValidarQuestoes(List<Integer> idQuestoes) {
        List<Questao> questoes = questaoDAO.findAllById(idQuestoes);
        if(questoes.size() != idQuestoes.size()) {
            throw new RuntimeException("Uma ou mais questões não foram encontradas.");
        }
        return questoes;
    }

    private void validarRevisoesEncontradas(List<Revisao> revisoes){
        if(revisoes.isEmpty()){
            throw new RuntimeException("Nenhuma revisão encontrada para o usuário");
        }
    }
}
