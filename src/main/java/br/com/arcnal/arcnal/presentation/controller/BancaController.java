package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.request.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Banca;
import br.com.arcnal.arcnal.application.service.IBancaService;
import br.com.arcnal.arcnal.presentation.controller.docs.BancaControllerDoc;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banca")
public class BancaController implements BancaControllerDoc {
    @Autowired
    IBancaService bancaService;

    @Override
    @PostMapping
    public ResponseEntity<Banca> adicionarBanca(@Valid @RequestBody BancaRequestDTO dto){
        return ResponseEntity.ok().body(bancaService.adicionarBanca(dto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Banca>> listarTodasBancas(){
        return ResponseEntity.ok().body(bancaService.listarTodasBancas());
    }
}
