package br.com.arcnal.arcnal.controllers;

import br.com.arcnal.arcnal.dtos.BancaRequestDTO;
import br.com.arcnal.arcnal.entities.Banca;
import br.com.arcnal.arcnal.services.IBancaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banca")
public class BancaController {
    @Autowired
    IBancaService bancaService;

    @PostMapping
    public ResponseEntity<Banca> adicionarBanca(@Valid @RequestBody BancaRequestDTO dto){
        return ResponseEntity.ok().body(bancaService.adicionarBanca(dto));
    }
}
