package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.response.EstatisticaUsuarioResponseDTO;
import br.com.arcnal.arcnal.application.service.IEstatisticaUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
@RequiredArgsConstructor
public class EstatisticaUsuarioController {

    private final IEstatisticaUsuarioService service;

    @GetMapping
    public ResponseEntity<EstatisticaUsuarioResponseDTO> dadosDashboard(){
        EstatisticaUsuarioResponseDTO dto = service.buscarEstatistica();
        return ResponseEntity.ok(dto);
    }
}
