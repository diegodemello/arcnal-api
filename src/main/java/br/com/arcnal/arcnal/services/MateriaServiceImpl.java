package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.AssuntoDAO;
import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dtos.AssuntoRespDTO;
import br.com.arcnal.arcnal.dtos.MateriaAssuntosRespDTO;
import br.com.arcnal.arcnal.dtos.MateriaReqDTO;
import br.com.arcnal.arcnal.entities.Assunto;
import br.com.arcnal.arcnal.entities.Materia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements IMateriaService{

    @Autowired
    MateriaDAO dao;
    @Autowired
    AssuntoDAO assuntoDAO;

    @Override
    public Materia criarMateriaSemAssuntos(MateriaReqDTO dto) {
        if(dao.existsByNome(dto.nome())){
            throw new RuntimeException("Erro ao salvar matéria.");
        }
        Materia materia = Materia.builder()
                .nome(dto.nome())
                .build();
        dao.save(materia);
        return materia;
    }

    @Override
    public MateriaAssuntosRespDTO listarAssuntosMateria(Integer idMateria) {
        Materia materia = dao.findById(idMateria)
                .orElseThrow(() -> new RuntimeException("Matéria não existe"));

        List<AssuntoRespDTO> assuntos = assuntoDAO.findAllByMateria_Id(idMateria)
                .stream()
                .map(assunto -> AssuntoRespDTO.builder()
                        .id(assunto.getId())
                        .nome(assunto.getNome())
                        .build())
                .toList();

        MateriaAssuntosRespDTO dto = MateriaAssuntosRespDTO.builder()
                .nome(materia.getNome())
                .assuntos(assuntos)
                .build();
        return dto;
    }
}
