package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruct.ProfesorMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/profesores")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
@ApiOperation(value = "Acciones sobre profesores", tags = "Acciones sobre Profesores")
public class ProfesorDTOController {
    @Autowired
    @Qualifier("profesorDAOImpl")
    private PersonaDAO personaDAO;
    @Autowired
    private ProfesorMapper mapper;

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar un profesor por su código")
    public ResponseEntity<?> obtenerProfesorPorId(@PathVariable Integer id){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Persona> optionalPersona = personaDAO.findById(id);

        PersonaDTO personaDTO = mapper.mapProfesor((Profesor)optionalPersona.get());

        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", personaDTO);

        return ResponseEntity.ok(mensaje);
    }
}
