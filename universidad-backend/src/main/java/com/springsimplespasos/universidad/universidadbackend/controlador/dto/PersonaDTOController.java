package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruct.AlumnoMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.mapstruct.Qualifier;

public class PersonaDTOController extends GenericDTOController<Persona, PersonaDAO> {
    protected final AlumnoMapper alumnoMapper;
    //protected final ProfesorMapper profesorMapper;
    //protected final EmpleadoMapper empleadoMapper;

    public PersonaDTOController(PersonaDAO service, String nombre_entidad, AlumnoMapper alumnoMapper) {
        super(service, nombre_entidad);
        this.alumnoMapper = alumnoMapper;
    }

    public PersonaDTO altaPersona(Persona persona){
        Persona personaEntidad = super.altaEntidad(persona);
        PersonaDTO personaDTO = null;
        if(personaEntidad instanceof Alumno) {
            personaDTO = alumnoMapper.mapAlumno((Alumno) personaEntidad);
        } else if(personaEntidad instanceof Profesor) {
            //mapper de profesor
        } else if(personaEntidad instanceof Empleado) {
            //mapper de empleado
        }
        return  personaDTO;
    }
}
