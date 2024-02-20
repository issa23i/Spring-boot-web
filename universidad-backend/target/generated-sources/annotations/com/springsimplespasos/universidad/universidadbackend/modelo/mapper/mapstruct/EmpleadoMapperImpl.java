package com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruct;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AlumnoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-20T13:50:19+0100",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.2 (Amazon.com Inc.)"
)
@Component
public class EmpleadoMapperImpl extends EmpleadoMapper {

    @Override
    public AlumnoDTO alumnoDTO(Alumno alumno) {
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
    public Alumno alumno(AlumnoDTO alumnoDTO) {
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
