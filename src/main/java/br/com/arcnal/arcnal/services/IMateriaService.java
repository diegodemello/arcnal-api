package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.MateriaAssuntosRespDTO;
import br.com.arcnal.arcnal.dtos.MateriaReqDTO;
import br.com.arcnal.arcnal.entities.Materia;

public interface IMateriaService {
    Materia criarMateriaSemAssuntos(MateriaReqDTO dto);
    MateriaAssuntosRespDTO listarAssuntosMateria(Integer idMateria);
}