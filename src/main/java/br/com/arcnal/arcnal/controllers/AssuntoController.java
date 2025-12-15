package br.com.arcnal.arcnal.controllers;

import br.com.arcnal.arcnal.dtos.AssuntoReqDTO;
import br.com.arcnal.arcnal.dtos.AssuntoRespDTO;
import br.com.arcnal.arcnal.dtos.AssuntosMateriaRespDTO;
import br.com.arcnal.arcnal.services.IAssuntoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssuntoController {

    @Autowired
    IAssuntoService assuntoService;

    @PostMapping("/assunto")
    public ResponseEntity<AssuntoRespDTO> criarAssunto(@Valid @RequestBody AssuntoReqDTO dto) {
        return ResponseEntity.ok().body(assuntoService.criarNovoAssunto(dto));
    }

    @GetMapping("/assuntos/materia/{idMateria}")
    public ResponseEntity<AssuntosMateriaRespDTO> listarAssuntosPorMateria(@Valid @RequestBody Integer idMateria) {
        return ResponseEntity.ok().body(assuntoService.listarAssuntosPorMateria(idMateria));
    }
}
