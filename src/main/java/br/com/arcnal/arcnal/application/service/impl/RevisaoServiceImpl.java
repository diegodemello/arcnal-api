package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.dto.request.AdicionarQuestaoRevisaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.DetalheRevisaoResponseDTO;
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
import io.micrometer.core.instrument.Counter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Qualifier("revisoesCriadas")
    private final Counter revisoesCriadas;

    @Override
    public void criarRevisao(RevisaoRequestDTO dto, String email) {

        Usuario usuario = buscarUsuarioPorEmail(email);
        List<Questao> questoes = buscarEValidarQuestoes(dto.idQuestoes());

        Revisao revisao = revisaoMapper.toEntity(dto);
        revisao.setUsuario(usuario);
        revisao.setQuestoes(questoes);

        revisaoRepository.save(revisao);
        revisoesCriadas.increment();
    }

    @Override
    @Transactional
    public void adicionarQuestao(UUID idRevisao, AdicionarQuestaoRevisaoRequestDTO dto) {
        List<Questao> questoes = buscarEValidarQuestoes(dto.idQuestoes());
        Revisao revisao = revisaoRepository.findById(idRevisao)
                .orElseThrow(() -> new RevisoesExistentesException("Não existe uma revisão para esse ID."));
        revisao.getQuestoes().addAll(questoes);
    }

    @Override
    public List<RevisaoResponseDTO> listarRevisoesPorUsuario(UUID idUsuario) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        List<Revisao> revisoes = buscarRevisoesPorUsuario(idUsuario);

        validarRevisoesEncontradas(revisoes);

        return revisaoMapper.toResponses(revisoes);
    }

    @Override
    public DetalheRevisaoResponseDTO listarRevisao(Integer pagina, Integer objetos, UUID idRevisao) {
        Revisao revisao = revisaoRepository.findById(idRevisao)
                .orElseThrow(() -> new RevisoesExistentesException("Revisão não encontrada com o ID: " + idRevisao));

        Pageable pageable = PageRequest.of(pagina, objetos);
        int inicio = (int) pageable.getOffset();
        int fim = Math.min((inicio + pageable.getPageSize()), revisao.getQuestoes().size());

        List<Questao> quesoesPaginadas = revisao.getQuestoes().subList(inicio, fim);

        return revisaoMapper.toDetalheResponse(revisao, quesoesPaginadas);
    }

    private Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findAllByEmailEndereco(email)
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
