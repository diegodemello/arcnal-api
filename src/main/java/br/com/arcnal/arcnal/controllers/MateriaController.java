package br.com.arcnal.arcnal.controllers;

import br.com.arcnal.arcnal.dtos.MateriaRequestDTO;
import br.com.arcnal.arcnal.entities.Materia;
import br.com.arcnal.arcnal.services.IMateriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    IMateriaService materiaService;

    @PostMapping
    public ResponseEntity<Materia> criarMateria(@Valid @RequestBody MateriaRequestDTO dto) {
        return ResponseEntity.ok().body(materiaService.criarMateriaSemAssuntos(dto));
    }
}