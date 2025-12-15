package br.com.arcnal.arcnal.controllers;

import br.com.arcnal.arcnal.dtos.QuestaoRequestDTO;
import br.com.arcnal.arcnal.dtos.QuestaoResponseDTO;
import br.com.arcnal.arcnal.services.IQuestaoService;
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

    @GetMapping("/listar")
    public ResponseEntity<List<QuestaoResponseDTO>> listarQuestoes(){
        return ResponseEntity.ok().body(questaoService.listarQuestoes());
    }
}
