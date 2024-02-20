package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruct;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AlumnoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-20T16:10:07+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.2 (Amazon.com Inc.)"
)
@Component
public class AlumnoMapperImpl extends AlumnoMapper {

    @Override
    public AlumnoDTO mapAlumno(Alumno alumno) {
        if ( alumno == null ) {
            return null;
        }

        AlumnoDTO alumnoDTO = new AlumnoDTO();

        alumnoDTO.setId( alumno.getId() );
        alumnoDTO.setNombre( alumno.getNombre() );
        alumnoDTO.setApellido( alumno.getApellido() );
        alumnoDTO.setDni( alumno.getDni() );
        alumnoDTO.setDireccion( alumno.getDireccion() );

        return alumnoDTO;
    }

    @Override
    public Alumno mapAlumno(AlumnoDTO alumnoDTO) {
        if ( alumnoDTO == null ) {
            return null;
        }

        Alumno alumno = new Alumno();

        alumno.setId( alumnoDTO.getId() );
        alumno.setNombre( alumnoDTO.getNombre() );
        alumno.setApellido( alumnoDTO.getApellido() );
        alumno.setDni( alumnoDTO.getDni() );
        alumno.setDireccion( alumnoDTO.getDireccion() );

        return alumno;
    }
}
