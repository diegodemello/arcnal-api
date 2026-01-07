package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.request.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.application.service.IMateriaService;
import br.com.arcnal.arcnal.presentation.controller.docs.MateriaControllerDoc;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materia")
public class MateriaController implements MateriaControllerDoc {

    @Autowired
    IMateriaService materiaService;

    @Override
    @PostMapping
    public ResponseEntity<Materia> criarMateria(@Valid @RequestBody MateriaRequestDTO dto) {
        return ResponseEntity.ok().body(materiaService.criarMateriaSemAssuntos(dto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Materia>> listarMaterias(){
        return ResponseEntity.ok().body(materiaService.listarTodasMaterias());
    }
}