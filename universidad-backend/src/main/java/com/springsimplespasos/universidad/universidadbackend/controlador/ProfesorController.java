package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones.ProfesorDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public ResponseEntity<?> buscarProfesoresPorCarrera (@PathVariable Integer idCarrera) {
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Carrera> optionalCarrera = carreraDAO.findById(idCarrera);
        if(optionalCarrera.isEmpty()) {
            //throw new BadRequestException(String.format("La carrera con id %d no existe", idCarrera));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("La carrera con id %d no existe", idCarrera));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Iterable<Persona> profesores = ((ProfesorDAOImpl)service).buscarProfesorPorCarrera(idCarrera);
        List<Persona> listaProfesores = (List<Persona>) profesores;
        if(listaProfesores.isEmpty()) {
            //throw new BadRequestException(String.format("No se encuentran profesores en la carrera con id %d", idCarrera));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No se encuentran profesores en la carrera con id %d", idCarrera));
            return ResponseEntity.badRequest().body(mensaje);
        }
        //return profesores;
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", profesores);
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/{idProfesor}/carrera/{idCarrera}")
    public ResponseEntity<?> asignarCarreraAProfesor (@PathVariable Integer idProfesor, @PathVariable Integer idCarrera){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Persona> optionalProfesor = service.findById(idProfesor);
        if(optionalProfesor.isEmpty()) {
            //throw new BadRequestException(String.format("Profesor con id %d no existe", idProfesor));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Profesor con id %d no existe", idProfesor));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Optional<Carrera> optionalCarrera = carreraDAO.findById(idCarrera);
        if(optionalCarrera.isEmpty()) {
            //throw new BadRequestException(String.format("La carrera con id %d no existe", idCarrera));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("La carrera con id %d no existe", idCarrera));
            return ResponseEntity.badRequest().body(mensaje);
        }

        Persona profesor = optionalProfesor.get();
        Carrera carrera = optionalCarrera.get();

        Set<Carrera> carrerasProfesor = ((Profesor) profesor).getCarreras();
        if(carrerasProfesor.isEmpty()) carrerasProfesor = new HashSet<>();
        carrerasProfesor.add(carrera);
        ((Profesor) profesor).setCarreras(carrerasProfesor);

        //return service.save(profesor);
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", service.save(profesor));
        return ResponseEntity.ok(mensaje);
    }
}
