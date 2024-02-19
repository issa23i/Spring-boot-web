package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/carreras")
public class CarreraController extends GenericController<Carrera, CarreraDAO> {



    @Autowired
    public CarreraController(CarreraDAO service) {
        super(service);
        nombreEntidad = "Carrera";
    }

/*

    @GetMapping("/{codigo}")
    public Carrera obtenerPorId(@PathVariable(value = "codigo", required = false) Integer id){
        Optional<Carrera> oCarrera = service.findById(id);
        if(!oCarrera.isPresent()){
            throw new BadRequestException(String.format("La carrera con id %d no existe", id));
        }
        return oCarrera.get();
    }*/

    @GetMapping("/nombre/{nombre}")
    public Iterable<Carrera> optenerPorNombre(@PathVariable(value = "nombre", required = false) String nombre){
        Iterable<Carrera> carreras = service.findCarrerasByNombreContainsIgnoreCase(nombre);
        List<Carrera> carreraList = (List<Carrera>) carreras;
        if(carreraList.isEmpty()) throw new BadRequestException(String.format("No existen %ss con el nombre %s.", nombreEntidad.toLowerCase(),nombre));
        return carreras;
    }

    @GetMapping("/profesores/nombre-apellido")
    public Iterable<Carrera> obtenerPorProfesorNombreYApellido(@RequestParam String nombre, @RequestParam String apellido){
        Iterable<Carrera> carreras = service.findCarrerasByProfesorNombreAndApellidoIgnoreCase(nombre,apellido);
        List<Carrera> carreraList = (List<Carrera>) carreras;
        if(carreraList.isEmpty()) throw new BadRequestException(String.format("No existen %ss con el profesor %s %s.", nombreEntidad.toLowerCase(),nombre, apellido));
        return carreras;
    }

    /*
        @PostMapping
        public ResponseEntity<?> altaCarrera(@Valid @RequestBody Carrera carrera, BindingResult result){
            /**if(carrera.getCantidadAnios()<1){
                throw new BadRequestException("El campo cantidad de anyos debe ser mayor a 0");
            }
            if(carrera.getCantidaMaterias()<1){
                throw new BadRequestException("El campo cantidad de materias debe ser mayor a 0");
            }*//*
        Map<String, Object> validaciones = new HashMap<>();
        if(result.hasErrors()){
            result.getFieldErrors()
                    .forEach(error -> validaciones.put(error.getField(),error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(validaciones);
        }

        validaciones.put("success", Boolean.TRUE);
        validaciones.put("datos",service.save(carrera));
        return ResponseEntity.ok(validaciones);
    }

    @PutMapping("/{id}")
    public Carrera actualizarCarrera (@PathVariable Integer id, @RequestBody Carrera carrera){
        Carrera carreraUpdate;
        Optional<Carrera> optionalCarrera = service.findById(id);
        if(optionalCarrera.isEmpty()){
            throw new BadRequestException(String.format("La carrera con id %d no existe", id));
        }
        carreraUpdate = optionalCarrera.get();
        carreraUpdate.setCantidadAnios(carrera.getCantidadAnios());
        carreraUpdate.setCantidaMaterias(carrera.getCantidaMaterias());

        return service.save(carreraUpdate);
    }
*//*
    @DeleteMapping("/{id}")
    public void eliminarCarrera (@PathVariable Integer id){
        service.deleteById(id);
    }*/
}
