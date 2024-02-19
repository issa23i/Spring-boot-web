package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.GenericoDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.function.EntityResponse;


import javax.persistence.Column;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public class GenericController<E, S extends GenericoDAO<E>> {

    protected final S service;
    protected String nombreEntidad;
    private E entidadUpdated;

    public GenericController(S service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        Map<String, Object> mensaje = new HashMap<>();

        List<E> listado = (List<E>) service.findAll();
        if (listado.isEmpty()) { //throw new BadRequestException(String.format("No se han encontrado %ss", nombreEntidad));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No se han encontrado %ss", nombreEntidad));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", listado);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Map<String, Object> mensaje = new HashMap<>();
        Optional<E> optionalE = service.findById(id);
        if (optionalE.isEmpty()) {
            //throw new BadRequestException(String.format("No se ha encontrado %s", nombreEntidad));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No se han encontrado %ss", nombreEntidad));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", optionalE.get());
        return ResponseEntity.ok(mensaje);
    }


    @DeleteMapping("/{id}")
    public void borrarPorId(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<?> altaDeEntidad(@Valid @RequestBody E entidad, BindingResult result) {
        Map<String, Object> mensaje = new HashMap<>();

        if (result.hasErrors()) {
            result.getFieldErrors()
                    .forEach(fieldError -> mensaje.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(mensaje);
        }

        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", service.save(entidad));
        return ResponseEntity.ok(mensaje);

    }

/**
 @PutMapping("/{id}") public E actualizarEntidad(@PathVariable Integer id, @RequestBody E entidad){
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
 **/
    /**
     *
     **/

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEntidad(@PathVariable Integer id, @RequestBody E entidad) {
        Map<String, Object> mensaje = new HashMap<>();
        Optional<E> optionalE = service.findById(id);
        if (optionalE.isEmpty()) {
            //throw new BadRequestException(String.format("%s con id %d no existe", nombreEntidad, id));
            mensaje.put("success", false);
            mensaje.put("mensaje", String.format("%s con id %d no existe", nombreEntidad, id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        E entidadUpdated = optionalE.get();
        BeanUtils.copyProperties(entidad, entidadUpdated, getIgnoredPropertyNames(entidad));
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", service.save(entidadUpdated));
        return ResponseEntity.ok(mensaje);
        //return service.save(entidadUpdated);
    }

    private String[] getIgnoredPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> ignoredNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
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
