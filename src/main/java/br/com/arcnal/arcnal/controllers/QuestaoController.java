package br.com.arcnal.arcnal.controllers;

import br.com.arcnal.arcnal.dtos.QuestaoReqDTO;
import br.com.arcnal.arcnal.dtos.QuestaoRespDTO;
import br.com.arcnal.arcnal.services.IQuestaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuestaoController {
    @Autowired
    IQuestaoService questaoService;

    @PostMapping("/questoes")
    public ResponseEntity<QuestaoRespDTO> adicionarQuestao(@Valid @RequestBody QuestaoReqDTO dto){
        return ResponseEntity.ok().body(questaoService.adicionarQuestao(dto));
    }

    @GetMapping("/questoes")
    public ResponseEntity<List<QuestaoRespDTO>> listarQuestoes(){
        return ResponseEntity.ok().body(questaoService.listarQuestoes());
    }
}
