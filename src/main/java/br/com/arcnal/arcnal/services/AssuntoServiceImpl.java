package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.AssuntoDAO;
import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dtos.AssuntoReqDTO;
import br.com.arcnal.arcnal.dtos.AssuntoRespDTO;
import br.com.arcnal.arcnal.entities.Assunto;
import br.com.arcnal.arcnal.entities.Materia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssuntoServiceImpl implements IAssuntoService{

    @Autowired
    AssuntoDAO assuntoDAO;
    @Autowired
    MateriaDAO materiaDAO;

    @Override
    public AssuntoRespDTO criarNovoAssunto(AssuntoReqDTO dto) {
        Materia materia = materiaDAO.findById(dto.idMateria())
                .orElseThrow(() -> new RuntimeException("Matéria não existe."));

        Assunto assunto = Assunto.builder()
                .nome(dto.nome())
                .materia(materia)
                .build();
        assuntoDAO.save(assunto);

        AssuntoRespDTO respDto = AssuntoRespDTO.builder()
                .id(assunto.getId())
                .nome(assunto.getNome())
                .build();
        return respDto;
    }
}
