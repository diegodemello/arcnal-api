package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.BancaDAO;
import br.com.arcnal.arcnal.dtos.BancaRequestDTO;
import br.com.arcnal.arcnal.entities.Banca;
import org.springframework.stereotype.Service;

@Service
public class BancaServiceImpl implements IBancaService {

    BancaDAO bancaDAO;
    public BancaServiceImpl(BancaDAO bancaDAO) {
        this.bancaDAO = bancaDAO;
    }

    @Override
    public Banca adicionarBanca(BancaRequestDTO dto) {
        System.out.println(dto.nome());
        if(bancaDAO.existsByNome(dto.nome())){
            throw new RuntimeException("Banca já cadastrada no sistema.");
        }

        Banca banca = Banca.builder()
                .nome(dto.nome())
                .build();
        return bancaDAO.save(banca);
    }
}