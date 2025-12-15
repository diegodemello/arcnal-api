package br.com.arcnal.arcnal.controllers;

import br.com.arcnal.arcnal.dtos.MateriaReqDTO;
import br.com.arcnal.arcnal.entities.Materia;
import br.com.arcnal.arcnal.services.IMateriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MateriaController {

    @Autowired
    IMateriaService materiaService;

    @PostMapping("/materia")
    public ResponseEntity<Materia> criarMateria(@Valid @RequestBody MateriaReqDTO dto) {
        return ResponseEntity.ok().body(materiaService.criarMateriaSemAssuntos(dto));
    }
}