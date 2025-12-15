package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.QuestaoDAO;
import br.com.arcnal.arcnal.dao.RevisaoDAO;
import br.com.arcnal.arcnal.dao.UsuarioDAO;
import br.com.arcnal.arcnal.dtos.RevisaoReqDTO;
import br.com.arcnal.arcnal.dtos.RevisaoRespDTO;
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
    public void criarRevisao(RevisaoReqDTO dto) {
        Usuario usuario = usuarioDAO.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Revisao revisao = revisaoMapper.requestToEntity(dto);
        revisao.setUsuario(usuario);

        List<Questao> questoes = questaoDAO.findAllById(dto.idQuestoes());
        if(questoes.size() != dto.idQuestoes().size()) {
            throw new RuntimeException("Uma ou mais questões não foram encontradas");
        }
        revisao.setQuestoes(questoes);
        revisaoDAO.save(revisao);
    }

    @Override
    public List<RevisaoRespDTO> listarRevisoesPorUsuario(UUID idUsuario) {
        Usuario usuario = usuarioDAO.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<Revisao> revisoes = revisaoDAO.findAllByUsuarioId(idUsuario);
        if(revisoes.isEmpty()){
            throw new RuntimeException("Nenhuma revisão encontrada para o usuário");
        }
        List<RevisaoRespDTO> revisaoRespDTOS = revisaoMapper.entityToResponse(revisoes);
        return revisaoRespDTOS;
    }
}
