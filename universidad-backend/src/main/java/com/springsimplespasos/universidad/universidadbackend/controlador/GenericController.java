package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.GenericoDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ReflectionUtils;


import javax.persistence.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public class GenericController <E, S extends GenericoDAO<E>>{

    protected final S service;
    protected String nombreEntidad;
    private E entidadUpdated;

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
    public E actualizarEntidad(@PathVariable Integer id, @RequestBody E entidad){
        Optional<E> optionalE = service.findById(id);
        if(optionalE.isEmpty()) throw new BadRequestException(String.format("%s con id %d no existe", nombreEntidad, id));
        E entidadUpdated = optionalE.get();
        try{
            Field[] fields = Stream.concat(Arrays.stream(entidad.getClass().getDeclaredFields()), Arrays.stream(entidad.getClass().getSuperclass().getDeclaredFields()))
                    .toArray(Field[]::new);
            for (Field field : fields){
                if(field.isAnnotationPresent(Column.class)){
                    // Configurar el campo para ser accesible (incluso si es privado)
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if(!columnAnnotation.unique()){
                        // Obtener el valor del campo
                        Object valorCampo = field.get(entidad);
                        // Obtener el método "set" correspondiente al campo
                        String nombreMetodo = "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                        Method metodo = entidadUpdated.getClass().getMethod(nombreMetodo, field.getType());
                        // Invocar el método "set" en entidadUpdated
                        metodo.invoke(entidadUpdated, valorCampo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.save(entidadUpdated);
    }
 **//** * **/

    @PutMapping("/{id}")
    public E actualizarEntidad(@PathVariable Integer id, @RequestBody E entidad){
        Optional<E> optionalE = service.findById(id);
        if(optionalE.isEmpty()) throw new BadRequestException(String.format("%s con id %d no existe", nombreEntidad, id));
        E entidadUpdated = optionalE.get();
        BeanUtils.copyProperties(entidad, entidadUpdated, getIgnoredPropertyNames(entidad));
        return service.save(entidadUpdated);
    }

    private String[] getIgnoredPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> ignoredNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) ignoredNames.add(pd.getName());
            else {
                Field field = ReflectionUtils.findField(source.getClass(), pd.getName());
                if (field != null && field.isAnnotationPresent(Column.class)) {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    if (columnAnnotation.unique()) ignoredNames.add(pd.getName());
                }
            }
        }
        String[] result = new String[ignoredNames.size()];
        return ignoredNames.toArray(result);
    }

}
