package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruct;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AlumnoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = EmpleadoMapperConfig.class)
public abstract class EmpleadoMapper {
    public abstract AlumnoDTO alumnoDTO(Alumno alumno);
    public abstract Alumno alumno(AlumnoDTO alumnoDTO);
}
