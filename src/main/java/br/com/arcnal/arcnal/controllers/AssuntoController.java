package br.com.arcnal.arcnal.controllers;

import br.com.arcnal.arcnal.dtos.AssuntoRequestDTO;
import br.com.arcnal.arcnal.dtos.AssuntoResponseDTO;
import br.com.arcnal.arcnal.dtos.AssuntosMateriaResponseDTO;
import br.com.arcnal.arcnal.services.IAssuntoService;
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
