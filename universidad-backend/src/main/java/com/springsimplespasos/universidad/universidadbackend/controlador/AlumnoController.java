package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController extends PersonaController {

    //private final PersonaDAO alumnoDAO;
    private final CarreraDAO carreraDAO;

    @Autowired
    public AlumnoController(@Qualifier("alumnoDAOImpl") PersonaDAO alumnoDAO, CarreraDAO carreraDAO) {
        super(alumnoDAO);
        nombreEntidad = "Alumno";
        this.carreraDAO = carreraDAO;
    }

/*

    @Autowired
    public AlumnoController(PersonaDAO alumnoDAO, CarreraDAO carreraDAO) {
        this.alumnoDAO = alumnoDAO;
        this.carreraDAO = carreraDAO;
    }


    @PostMapping
    public Persona altaAlumno(@RequestBody Persona alumno){
        return alumnoDAO.save(alumno);
    }

    @GetMapping("/{id}")
    public Persona obtenerAlumnoPorId(@PathVariable(required = false) Integer id){
        Optional<Persona> optionalAlumno = alumnoDAO.findById(id);
        if(optionalAlumno.isEmpty()) throw new BadRequestException(String.format("Alumno con id %d no existe", id));
        return optionalAlumno.get();
    }

    @GetMapping
    public List<Persona> obtenerTodos(){
        List<Persona> alumnos = (List<Persona>) alumnoDAO.findAll();
        if(alumnos.isEmpty()){
            throw new BadRequestException("No existen alumnos");
        }
        return alumnos;
    }

    @PutMapping("/{id}")
    public Persona actualizarAlumno(@PathVariable Integer id, @RequestBody Persona alumno){
        Persona alumnoUpdate = null;
        Optional<Persona> optionalAlumno = service.findById(id);
        if(optionalAlumno.isEmpty()) throw new BadRequestException(String.format("Alumno con id %d no existe", id));
        alumnoUpdate = optionalAlumno.get();
        alumnoUpdate.setNombre(alumno.getNombre());
        alumnoUpdate.setApellido(alumno.getApellido());
        alumnoUpdate.setDireccion(alumno.getDireccion());
        return service.save(alumnoUpdate);
    }*/
/*
    @DeleteMapping("/{id}")
    public void eliminarAlumno (@PathVariable Integer id){
        alumnoDAO.deleteById(id);
    }*/

    @PutMapping("/{idAlumno}/carrera/{idCarrera}")
    public Persona asignarCarreraAlumno(@PathVariable Integer idAlumno, @PathVariable Integer idCarrera){
        Optional<Persona> optionalAlumno = service.findById(idAlumno);
        if(optionalAlumno.isEmpty()) throw new BadRequestException(String.format("Alumno con id %d no existe", idAlumno));
        Optional<Carrera> optionalCarrera = carreraDAO.findById(idCarrera);
        if(optionalCarrera.isEmpty()) throw new BadRequestException(String.format("La carrera con id %d no existe", idCarrera));

        Persona alumno = optionalAlumno.get();
        Carrera carrera = optionalCarrera.get();

        ((Alumno)alumno).setCarrera(carrera);

        return service.save(alumno);
    }

}
