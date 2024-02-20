package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.AlumnoDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.dto.PersonaDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruct.AlumnoMapper;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AlumnoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import io.swagger.annotations.*;
import org.mapstruct.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alumnos")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
@Api(value = "Acciones relacionadas con los alumnos", tags = "Acciones sobre alumnos")
public class AlumnoDTOController extends PersonaDTOController {

    public AlumnoDTOController( AlumnoDAO service, AlumnoMapper alumnoMapper) {
        super(service, "Alumno", alumnoMapper);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar un alumno por su código")
    public ResponseEntity<?> obtenerAlumnoPorId(@PathVariable @ApiParam(name = "Código del alumno") Integer id){
        Map<String, Object> mensaje = new HashMap<>();
        //Optional<Persona> optionalPersona = personaDAO.findById(id);

        //PersonaDTO personaDTO = mapper.mapAlumno((Alumno) optionalPersona.get());

        mensaje.put("success", Boolean.TRUE);
        //mensaje.put("data", personaDTO);

        return ResponseEntity.ok(mensaje);
    }

    @PostMapping
    @ApiOperation(value = "Añadir una nueva carrera")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Ejecutado satisfactoriamente")
            }
    )
    public  ResponseEntity<?> altaAlumno(@Valid @RequestBody PersonaDTO personaDTO, BindingResult result){
        Map<String, Object> mensaje = new HashMap<>();
       if(result.hasErrors()){
           mensaje.put("success", Boolean.FALSE);
           mensaje.put("validaciones", super.obtenerValidaciones(result));
           return ResponseEntity.badRequest().body(mensaje);
       }
       PersonaDTO save = super.altaPersona(alumnoMapper.mapAlumno((AlumnoDTO) personaDTO));

       mensaje.put("succes", Boolean.TRUE);
       mensaje.put("data", save);
       return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
    }
}
