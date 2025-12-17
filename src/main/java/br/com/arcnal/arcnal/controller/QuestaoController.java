package br.com.arcnal.arcnal.controller;

import br.com.arcnal.arcnal.dto.*;
import br.com.arcnal.arcnal.service.IQuestaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questao")
public class QuestaoController {
    @Autowired
    IQuestaoService questaoService;

    @PostMapping
    public ResponseEntity<QuestaoResponseDTO> adicionarQuestao(@Valid @RequestBody QuestaoRequestDTO dto){
        return ResponseEntity.ok().body(questaoService.adicionarQuestao(dto));
    }

    @GetMapping
    public ResponseEntity<List<QuestaoResponseDTO>> listarQuestoes(){
        return ResponseEntity.ok().body(questaoService.listarQuestoes());
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<QuestaoResponseDTO>> listarQuestoesPorBancaAnoMateriaAssunto(
            @RequestParam(required = false) Integer idBanca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) Integer idMateria,
            @RequestParam(required = false) Integer idAssunto){
        return ResponseEntity.ok().body(questaoService.listarQuestoesPorFiltro(idBanca, ano, idMateria, idAssunto));
    }

    @PostMapping("/{id}/responder")
    public ResponseEntity<RespostaQuestaoResponseDTO> responderQuestao(@Valid @PathVariable Integer id, @RequestBody RespostaQuestaoRequestDTO request){
        return ResponseEntity.ok().body(questaoService.responderQuestao(id, request.alternativaEscolhida()));
    }

    @GetMapping("/{id}/resolucao")
    public ResponseEntity<ResolucaoQuestaoResponseDTO> obterResolucaoQuestao(@PathVariable Integer id){
        return ResponseEntity.ok().body(questaoService.obterResolucaoQuestao(id));
    }
}