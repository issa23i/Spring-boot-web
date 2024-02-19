package com.springsimplespasos.universidad.universidadbackend.modelo.mapper;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.CarreraDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;

public class CarreraMapper {
    public static CarreraDTO mapCarrera(Carrera carrera){
        CarreraDTO dto = new CarreraDTO();
        dto.setCodigo(carrera.getId());
        dto.setNombre(carrera.getNombre());
        dto.setCamtidad_anyos(carrera.getCantidadAnios());
        dto.setCantidad_materias(carrera.getCantidaMaterias());
        return dto;
    }
}
