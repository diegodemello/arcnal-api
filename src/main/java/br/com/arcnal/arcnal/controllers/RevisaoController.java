package br.com.arcnal.arcnal.controllers;

import br.com.arcnal.arcnal.dtos.RevisaoReqDTO;
import br.com.arcnal.arcnal.dtos.RevisaoRespDTO;
import br.com.arcnal.arcnal.services.IRevisaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class RevisaoController {
    @Autowired
    IRevisaoService revisaoService;

    @PostMapping("/revisao")
    public ResponseEntity<Void> criarRevisao(@Valid @RequestBody RevisaoReqDTO dto){
        revisaoService.criarRevisao(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/revisao/{idUsuario}")
    public ResponseEntity<List<RevisaoRespDTO>> listarRevisoesPorUsuario(@PathVariable UUID idUsuario){
        return ResponseEntity.ok().body(revisaoService.listarRevisoesPorUsuario(idUsuario));
    }
}
