package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.*;
import br.com.arcnal.arcnal.application.service.IQuestaoService;
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
    public ResponseEntity<List<QuestaoResponseDTO>> listarQuestoes(@RequestParam Integer pagina, Integer objetos){
        return ResponseEntity.ok().body(questaoService.listarQuestoes(pagina, objetos));
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<QuestaoResponseDTO>> listarQuestoesPorBancaAnoMateriaAssunto(
            @RequestParam Integer pagina, Integer objetos,
            @RequestParam(required = false) Integer idBanca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) Integer idMateria,
            @RequestParam(required = false) Integer idAssunto){
        return ResponseEntity.ok().body(questaoService.listarQuestoesPorFiltro(pagina, objetos, idBanca, ano, idMateria, idAssunto));
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