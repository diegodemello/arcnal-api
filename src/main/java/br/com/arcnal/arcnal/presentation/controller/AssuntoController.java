package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.AssuntoRequestDTO;
import br.com.arcnal.arcnal.application.dto.AssuntoResponseDTO;
import br.com.arcnal.arcnal.application.dto.AssuntosMateriaResponseDTO;
import br.com.arcnal.arcnal.application.service.IAssuntoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assunto")
public class AssuntoController {

    @Autowired
    IAssuntoService assuntoService;

    @PostMapping
    public ResponseEntity<AssuntoResponseDTO> criarAssunto(@Valid @RequestBody AssuntoRequestDTO dto) {
        return ResponseEntity.ok().body(assuntoService.criarNovoAssunto(dto));
    }

    @GetMapping("/materia/listar/{idMateria}")
    public ResponseEntity<AssuntosMateriaResponseDTO> listarAssuntosPorMateria(@Valid @PathVariable Integer idMateria) {
        return ResponseEntity.ok().body(assuntoService.listarAssuntosPorMateria(idMateria));
    }
}
