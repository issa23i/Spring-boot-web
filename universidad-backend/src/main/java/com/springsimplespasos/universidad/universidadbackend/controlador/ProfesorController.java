package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones.ProfesorDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profesores")
public class ProfesorController extends PersonaController {

    private final CarreraDAO carreraDAO;
    @Autowired
    public ProfesorController(@Qualifier("profesorDAOImpl")PersonaDAO service, CarreraDAO carreraDAO) {
        super(service);
        this.carreraDAO = carreraDAO;
        nombreEntidad = "Profesor";
    }


    @GetMapping("/carrera/{idCarrera}")
    public Iterable<Persona> buscarProfesoresPorCarrera (@PathVariable Integer idCarrera) {
        Optional<Carrera> optionalCarrera = carreraDAO.findById(idCarrera);
        if(optionalCarrera.isEmpty()) throw new BadRequestException(String.format("La carrera con id %d no existe", idCarrera));
        Iterable<Persona> profesores = ((ProfesorDAOImpl)service).buscarProfesorPorCarrera(idCarrera);
        List<Persona> listaProfesores = (List<Persona>) profesores;
        if(listaProfesores.isEmpty()) throw new BadRequestException(String.format("No se encuentran profesores en la carrera con id %d", idCarrera));
        return profesores;
    }
}
