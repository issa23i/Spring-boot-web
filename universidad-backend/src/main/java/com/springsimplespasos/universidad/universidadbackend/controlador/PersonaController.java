package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public class PersonaController extends GenericController<Persona, PersonaDAO> {
    public PersonaController(PersonaDAO service) {
        super(service);
    }

    @GetMapping("/nombre-apellido")
    public Persona buscarPersonaPorNombreYApellido(@RequestParam String nombre, @RequestParam String apellido){
        Optional<Persona> optionalPersona = service.findFirstByNombreAndApellido(nombre, apellido);
        if(optionalPersona.isEmpty()) throw new RuntimeException(String.format("No se encontró Persona con nombre %s y apellido %s", nombre, apellido));
        return optionalPersona.get();
    }
}
