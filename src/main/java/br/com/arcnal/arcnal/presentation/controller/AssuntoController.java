package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.request.AssuntoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.AssuntoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.AssuntosMateriaResponseDTO;
import br.com.arcnal.arcnal.application.service.IAssuntoService;
import br.com.arcnal.arcnal.presentation.controller.docs.AssuntoControllerDoc;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assunto")
public class AssuntoController implements AssuntoControllerDoc {

    @Autowired
    IAssuntoService assuntoService;

    @Override
    @PostMapping
    public ResponseEntity<AssuntoResponseDTO> criarAssunto(@Valid @RequestBody AssuntoRequestDTO dto) {
        return ResponseEntity.ok().body(assuntoService.criarNovoAssunto(dto));
    }

    @Override
    @GetMapping("/materia/listar/{idMateria}")
    public ResponseEntity<AssuntosMateriaResponseDTO> listarAssuntosPorMateria(@Valid @PathVariable Integer idMateria) {
        return ResponseEntity.ok().body(assuntoService.listarAssuntosPorMateria(idMateria));
    }
}
