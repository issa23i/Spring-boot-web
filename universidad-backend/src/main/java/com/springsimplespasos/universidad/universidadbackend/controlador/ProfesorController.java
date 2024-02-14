package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones.ProfesorDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @PutMapping("/{idProfesor}/carrera/{idCarrera}")
    public Persona asignarCarreraAProfesor (@PathVariable Integer idProfesor, @PathVariable Integer idCarrera){
        Optional<Persona> optionalProfesor = service.findById(idProfesor);
        if(optionalProfesor.isEmpty()) throw new BadRequestException(String.format("Profesor con id %d no existe", idProfesor));
        Optional<Carrera> optionalCarrera = carreraDAO.findById(idCarrera);
        if(optionalCarrera.isEmpty()) throw new BadRequestException(String.format("La carrera con id %d no existe", idCarrera));

        Persona profesor = optionalProfesor.get();
        Carrera carrera = optionalCarrera.get();

        Set<Carrera> carrerasProfesor = ((Profesor) profesor).getCarreras();
        if(carrerasProfesor.isEmpty()) carrerasProfesor = new HashSet<>();
        carrerasProfesor.add(carrera);
        ((Profesor) profesor).setCarreras(carrerasProfesor);

        return service.save(profesor);
    }
}
