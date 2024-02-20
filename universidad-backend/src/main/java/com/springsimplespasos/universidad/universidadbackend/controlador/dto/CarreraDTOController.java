package com.springsimplespasos.universidad.universidadbackend.controlador.dto;

import com.springsimplespasos.universidad.universidadbackend.modelo.dto.CarreraDTO;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.CarreraMapper;
import com.springsimplespasos.universidad.universidadbackend.modelo.mapper.mapstruct.CarreraMapperMS;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carreras")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
@Api(value = "Acciones relacionadas con las carreras", tags = "Acciones sobre carreras")
public class CarreraDTOController extends GenericDTOController<Carrera, CarreraDAO> {

    @Autowired
    private CarreraMapperMS mapper;

    public CarreraDTOController(CarreraDAO service) {
        super(service, "Carrera");
    }

    @GetMapping
    @ApiOperation(value = "Consultar todas las carreras")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Ejecutado satisfactoriamente")
            }
    )
    public ResponseEntity<?> obtenerCarreras(){
        Map<String,Object> mensaje = new HashMap<>();

        List<Carrera> carreras = super.obtenerTodos();

        if(carreras.isEmpty()){
            mensaje.put("success",Boolean.FALSE);
            mensaje.put("mensaje", String.format("No se encontraron %ss cargados", nombre_entidad));
            return ResponseEntity.badRequest().body(mensaje);
        }

        List<CarreraDTO> carreraDTOS = carreras
                .stream()
                .map(mapper::mapCarrera)
                .collect(Collectors.toList());
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("data", carreraDTOS);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar carrera por código")
    public ResponseEntity<?> obtenerCarreraPorId(@PathVariable @ApiParam(name = "Código del sistema") Integer id){
        return ResponseEntity.ok("");
    }

    @PostMapping
    @ApiOperation(value = "Dar de alta una nueva carrera")
    public ResponseEntity<?> altaCarrera(@RequestBody @ApiParam(name = "Carrera de la universidad") CarreraDTO carreraDTO){
        return ResponseEntity.ok("");
    }
}
