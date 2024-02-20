package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruct;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.ProfesorDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.ProfesorDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = ProfesorMapperConfig.class)
public abstract class ProfesorMapper {
    public abstract ProfesorDTO mapProfesor(Profesor profesor);
    public abstract Profesor mapProfesor(ProfesorDTO profesorDTO);
}
