package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.BancaDAO;
import br.com.arcnal.arcnal.dtos.BancaRequestDTO;
import br.com.arcnal.arcnal.entities.Banca;
import br.com.arcnal.arcnal.mapper.BancaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BancaServiceImpl implements IBancaService {

    private final BancaDAO bancaDAO;
    private final BancaMapper bancaMapper;

    @Override
    public Banca adicionarBanca(BancaRequestDTO dto) {
        System.out.println(dto.nome());
        if(bancaDAO.existsByNome(dto.nome())){
            throw new RuntimeException("Banca já cadastrada no sistema.");
        }

        Banca banca = bancaMapper.requestToEntity(dto);
        return bancaDAO.save(banca);
    }
}