package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.request.RevisaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.RevisaoResponseDTO;
import br.com.arcnal.arcnal.application.service.IRevisaoService;
import br.com.arcnal.arcnal.presentation.controller.docs.RevisaoControllerDoc;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/revisao")
public class RevisaoController implements RevisaoControllerDoc {
    @Autowired
    IRevisaoService revisaoService;

    @Override
    @PostMapping
    public ResponseEntity<Void> criarRevisao(@Valid @RequestBody RevisaoRequestDTO dto,
                                             @AuthenticationPrincipal UserDetails userDetails){
        String emailUsuario = extrairEmailDoToken(userDetails);
        revisaoService.criarRevisao(dto, emailUsuario);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<List<RevisaoResponseDTO>> listarRevisoesPorUsuario(@PathVariable UUID idUsuario){
        return ResponseEntity.ok().body(revisaoService.listarRevisoesPorUsuario(idUsuario));
    }

    private String extrairEmailDoToken(UserDetails userDetails){
        return userDetails.getUsername();
    }
}
