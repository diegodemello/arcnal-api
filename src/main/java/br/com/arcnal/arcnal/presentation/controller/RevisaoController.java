package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.request.AdicionarQuestaoRevisaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.request.RevisaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.DetalheRevisaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.RevisaoResponseDTO;
import br.com.arcnal.arcnal.application.service.IRevisaoService;
import br.com.arcnal.arcnal.presentation.controller.docs.RevisaoControllerDoc;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String emailUsuario = extrairEmailDoToken();
        revisaoService.criarRevisao(dto, emailUsuario);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idRevisao}")
    public ResponseEntity<Void> adicionarQuestoesParaUmaRevisao(@PathVariable UUID idRevisao, @RequestBody AdicionarQuestaoRevisaoRequestDTO dto){
        revisaoService.adicionarQuestao(idRevisao, dto);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping("/listar/{idUsuario}")
    public ResponseEntity<List<RevisaoResponseDTO>> listarRevisoesPorUsuario(@PathVariable UUID idUsuario){
        return ResponseEntity.ok().body(revisaoService.listarRevisoesPorUsuario(idUsuario));
    }

    @GetMapping("/{idRevisao}")
    public ResponseEntity<DetalheRevisaoResponseDTO> listarRevisao(
            @RequestParam Integer pagina,
            @RequestParam Integer objetos,
            @PathVariable UUID idRevisao){
        return ResponseEntity.ok().body(revisaoService.listarRevisao(pagina, objetos, idRevisao));
    }

    private String extrairEmailDoToken(){
        var autenticacao = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacao != null && autenticacao.getPrincipal() instanceof String){
            return (String) autenticacao.getPrincipal();
        } else {
            throw new IllegalStateException("Não foi possível extrair o email do token.");
        }
    }
}
