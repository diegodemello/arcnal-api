package br.com.arcnal.arcnal.controller;

import br.com.arcnal.arcnal.dto.RevisaoRequestDTO;
import br.com.arcnal.arcnal.dto.RevisaoResponseDTO;
import br.com.arcnal.arcnal.service.IRevisaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/revisao")
public class RevisaoController {
    @Autowired
    IRevisaoService revisaoService;

    @PostMapping
    public ResponseEntity<Void> criarRevisao(@Valid @RequestBody RevisaoRequestDTO dto){
        revisaoService.criarRevisao(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<List<RevisaoResponseDTO>> listarRevisoesPorUsuario(@PathVariable UUID idUsuario){
        return ResponseEntity.ok().body(revisaoService.listarRevisoesPorUsuario(idUsuario));
    }
}
