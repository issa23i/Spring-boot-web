package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruct;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.ProfesorDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-20T13:50:20+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.2 (Amazon.com Inc.)"
)
@Component
public class ProfesorMapperImpl extends ProfesorMapper {

    @Override
    public ProfesorDTO mapProfesor(Profesor profesor) {
        if ( profesor == null ) {
            return null;
        }

        ProfesorDTO profesorDTO = new ProfesorDTO();

        profesorDTO.setId( profesor.getId() );
        profesorDTO.setNombre( profesor.getNombre() );
        profesorDTO.setApellido( profesor.getApellido() );
        profesorDTO.setDni( profesor.getDni() );
        profesorDTO.setDireccion( profesor.getDireccion() );
        profesorDTO.setSueldo( profesor.getSueldo() );

        return profesorDTO;
    }

    @Override
    public Profesor mapProfesor(ProfesorDTO profesorDTO) {
        if ( profesorDTO == null ) {
            return null;
        }

        Profesor profesor = new Profesor();

        profesor.setId( profesorDTO.getId() );
        profesor.setNombre( profesorDTO.getNombre() );
        profesor.setApellido( profesorDTO.getApellido() );
        profesor.setDni( profesorDTO.getDni() );
        profesor.setDireccion( profesorDTO.getDireccion() );
        profesor.setSueldo( profesorDTO.getSueldo() );

        return profesor;
    }
}
