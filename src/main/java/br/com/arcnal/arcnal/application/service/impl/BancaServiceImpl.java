package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.service.IBancaService;
import br.com.arcnal.arcnal.domain.repositories.BancaRepository;
import br.com.arcnal.arcnal.application.dto.request.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Banca;
import br.com.arcnal.arcnal.domain.exception.BancaExistenteException;
import br.com.arcnal.arcnal.application.mapper.BancaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BancaServiceImpl implements IBancaService {

    private final BancaRepository bancaRepository;
    private final BancaMapper bancaMapper;

    @Override
    public Banca adicionarBanca(BancaRequestDTO dto) {
        if(bancaRepository.existsByNome(dto.nome())){
            throw new BancaExistenteException("Banca com nome " + dto.nome() + " já existe.");
        }
        return bancaRepository.save(bancaMapper.toEntity(dto));
    }

    @Override
    public List<Banca> listarTodasBancas() {
        return bancaRepository.findAll();
    }
}