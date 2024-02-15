package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class PersonaController extends GenericController<Persona, PersonaDAO> {
    public PersonaController(PersonaDAO service) {
        super(service);
        nombreEntidad = "Persona";
    }

    @GetMapping("/nombre-apellido")
    public Persona buscarPersonaPorNombreYApellido(@RequestParam String nombre, @RequestParam String apellido){
        Optional<Persona> optionalPersona = service.findFirstByNombreAndApellido(nombre, apellido);
        if(optionalPersona.isEmpty()) throw new RuntimeException(String.format("No se encontró %s con nombre %s y apellido %s",nombreEntidad.toLowerCase(), nombre, apellido));
        return optionalPersona.get();
    }

    @GetMapping("/apellido")
    public Iterable<Persona> buscarPersonaPorApellido(@RequestParam String apellido){
        Iterable<Persona> personas = service.buscarPersonaPorApellido(apellido);
        boolean noExistenPersonas = ((List<Persona>) personas).isEmpty();
        if(noExistenPersonas) throw new BadRequestException(String.format("No se encontraron %ss con el apellido %s.", nombreEntidad.toLowerCase(), apellido));
        return personas;
    }

    @GetMapping("/dni")
    public Persona buscarPersonaPorDni(@RequestParam String dni){
        Optional<Persona> persona = service.buscarPorDni(dni);
        if(persona.isEmpty()) throw new BadRequestException(String.format("No se encontraron %ss con el dni %s.", nombreEntidad.toLowerCase(), dni));
        return persona.get();
    }

    @PutMapping("/{id}")
    public Persona actualizarPersona(@PathVariable Integer id, @RequestBody Persona p){
        Persona pUpdate = null;
        try{
            Optional<Persona> optionalPersona = service.findById(id);
            if(optionalPersona.isEmpty()) throw new BadRequestException(String.format("%s con id %d no existe",nombreEntidad, id));
            // campos Persona
            pUpdate = optionalPersona.get();
            pUpdate.setNombre(p.getNombre());
            pUpdate.setApellido(p.getApellido());
            pUpdate.setDireccion(p.getDireccion());
            // campos específicos
            Field[] fields = p.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (!columnAnnotation.nullable()) {
                        // Configurar el campo para ser accesible (incluso si es privado)
                        field.setAccessible(true);

                        try {
                            // Obtener el valor del campo
                            Object valorCampo = field.get(p);

                            // Verificar si el valor del campo es nulo
                            if (valorCampo == null) {
                                throw new BadRequestException(String.format("El campo %s de la entidad %s no puede ser nulo", field.getName(), p.getClass().getSimpleName()));
                            }
                        } catch (IllegalAccessException e) {
                            // Manejar la excepción si no se puede acceder al campo
                            e.printStackTrace();
                        }
                    }
                }
            }

        } catch (IllegalAccessError e){
            e.printStackTrace();
        }
        return service.save(pUpdate);

    }

    @Override
    @GetMapping
    public List<Persona> obtenerTodos(){
        List<Persona> listado = (List<Persona>) service.findAll();
        if(listado.isEmpty()) throw new BadRequestException(String.format("No se han encontrado %ss", nombreEntidad));
        listado.removeIf(p -> !p.getClass().getSimpleName().equals(nombreEntidad));
        return listado;
    }

}
