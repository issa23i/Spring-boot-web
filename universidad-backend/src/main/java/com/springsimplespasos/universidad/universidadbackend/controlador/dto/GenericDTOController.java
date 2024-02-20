package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.GenericoDAO;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class GenericDTOController <E, S extends GenericoDAO<E>> {
    protected final S service;
    protected final String nombre_entidad;

    public List<E> obtenerTodos(){
        return (List<E>) service.findAll();
    }

    public E obtenerPorId(Integer id){
        return (E) service.findById(id);
    }

    public E altaEntidad(E entidad){
        return service.save(entidad);
    }

    protected Map<String, Object> obtenerValidaciones(BindingResult result){
        Map<String, Object> validaciones = new HashMap<>();
        result.getFieldErrors()
                .forEach(fieldError -> validaciones.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return validaciones;
    }
}
