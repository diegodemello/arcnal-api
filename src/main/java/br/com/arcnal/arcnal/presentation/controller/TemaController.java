package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.request.TemaRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.TemaResponseDTO;
import br.com.arcnal.arcnal.application.service.ITemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tema")
@RequiredArgsConstructor
public class TemaController {

    private final ITemaService service;

    @PostMapping
    public ResponseEntity<Void> criarTema(TemaRequestDTO request){
        service.criarTema(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<TemaResponseDTO>> listarTemas(){
        return ResponseEntity.ok(service.listarTemas());
    }

    @DeleteMapping("{idTema}")
    public ResponseEntity<Void> deletarTema(@PathVariable Integer idTema){
        service.deletarTema(idTema);
        return ResponseEntity.noContent().build();
    }
}
