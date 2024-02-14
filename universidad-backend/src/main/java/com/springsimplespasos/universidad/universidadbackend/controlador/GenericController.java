package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.GenericoDAO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

public class GenericController <E, S extends GenericoDAO<E>>{

    protected final S service;
    protected String nombreEntidad;

    public GenericController(S service) {
        this.service = service;
    }

    @GetMapping
    public List<E> obtenerTodos(){
        List<E> listado = (List<E>) service.findAll();
        if(listado.isEmpty()) throw new BadRequestException(String.format("No se han encontrado %ss", nombreEntidad));
        return listado;
    }

    @GetMapping("/{id}")
    public E obtenerPorId(@PathVariable Integer id){
        Optional<E> optionalE = service.findById(id);
        if(optionalE.isEmpty()) throw new BadRequestException(String.format("No se ha encontrado %s", nombreEntidad));
        return optionalE.get();
    }


    @DeleteMapping("/{id}")
    public void borrarPorId(@PathVariable Integer id){
        service.deleteById(id);
    }

    @PostMapping
    public E altaDeEntidad(@RequestBody E entidad){
        try {
            // Obtener los campos de la entidad
            Field[] fields = entidad.getClass().getDeclaredFields();

            // Iterar sobre los campos y verificar la presencia de la anotación @Column con nullable=false
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (!columnAnnotation.nullable()) {
                        // Configurar el campo para ser accesible (incluso si es privado)
                        field.setAccessible(true);

                        try {
                            // Obtener el valor del campo
                            Object valorCampo = field.get(entidad);

                            // Verificar si el valor del campo es nulo
                            if (valorCampo == null) {
                                throw new BadRequestException(String.format("El campo %s de la entidad %s no puede ser nulo", field.getName(), entidad.getClass().getSimpleName()));
                            }
                        } catch (IllegalAccessException e) {
                            // Manejar la excepción si no se puede acceder al campo
                            e.printStackTrace();
                        }
                    }
                }
            }
            return service.save(entidad);
        } catch (DataIntegrityViolationException e) {
            // Capturar la excepción si se lanza una DataIntegrityViolationException
            Throwable causa = e.getRootCause();
            String mensajeCausa = causa!=null? causa.getMessage() : "";
            throw new BadRequestException("Error en la solicitud. No se pudo crear la entidad " + nombreEntidad.toLowerCase() + ". - " + mensajeCausa);
        }
    }
/**
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
 }

@PutMapping("/{id}")
public E actualizarEntidad(@PathVariable Integer id, @RequestBody E entidad){
    Optional<E> optionalE = service.findById(id);
    if(optionalE.isEmpty()) throw new BadRequestException(String.format("%s con id %d no existe", nombreEntidad, id));
    return optionalE.get();
}
 * **/
}
