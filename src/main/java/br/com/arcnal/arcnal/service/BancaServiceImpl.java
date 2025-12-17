package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dao.BancaDAO;
import br.com.arcnal.arcnal.dto.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.Banca;
import br.com.arcnal.arcnal.exception.BancaExistenteException;
import br.com.arcnal.arcnal.mapper.BancaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BancaServiceImpl implements IBancaService {

    private final BancaDAO bancaDAO;
    private final BancaMapper bancaMapper;

    @Override
    public Banca adicionarBanca(BancaRequestDTO dto) {
        if(bancaDAO.existsByNome(dto.nome())){
            throw new BancaExistenteException("Banca com nome " + dto.nome() + " já existe.");
        }
        return bancaDAO.save(bancaMapper.toEntity(dto));
    }

    @Override
    public List<Banca> listarTodasBancas() {
        return bancaDAO.findAll();
    }
}