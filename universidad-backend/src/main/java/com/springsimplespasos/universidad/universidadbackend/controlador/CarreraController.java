package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carreras")
public class CarreraController extends GenericController<Carrera, CarreraDAO> {



    @Autowired
    public CarreraController(CarreraDAO service) {
        super(service);
        nombreEntidad = "Carrera";
    }



    @GetMapping("/{codigo}")
    public Carrera obtenerPorId(@PathVariable(value = "codigo", required = false) Integer id){
        Optional<Carrera> oCarrera = service.findById(id);
        if(!oCarrera.isPresent()){
            throw new BadRequestException(String.format("La carrera con id %d no existe", id));
        }
        return oCarrera.get();
    }

    /*@PostMapping
    public Carrera altaCarrera(@RequestBody Carrera carrera){
        if(carrera.getCantidadAnios()<1){
            throw new BadRequestException("El campo cantidad de anyos debe ser mayor a 0");
        }
        if(carrera.getCantidaMaterias()<1){
            throw new BadRequestException("El campo cantidad de materias debe ser mayor a 0");
        }
        return service.save(carrera);
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
