package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aulas")
public class AulaController extends GenericController<Aula, AulaDAO> {
    @Autowired
    public AulaController(AulaDAO service) {
        super(service);
        nombreEntidad = "Aula";
    }

    @GetMapping("/pizarron/{pizarron}")
    ResponseEntity<?> findAulasByPizarron(@PathVariable String pizarron){
        Pizarron tipoPizarron = Pizarron.valueOf(pizarron.toUpperCase());
        Map<String, Object> mensaje = new HashMap<>();
        Iterable<Aula> aulas = service.findAulasByPizarron(tipoPizarron);
        List<Aula> aulaList = (List<Aula>) aulas;
        if(aulaList.isEmpty()) {
            //throw new BadRequestException(String.format("No se encontraron %s por tipo de pizarrón %s",nombreEntidad.toLowerCase(),pizarron.toLowerCase()));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No se encontraron %s por tipo de pizarrón %s",nombreEntidad.toLowerCase(),pizarron.toLowerCase()));
            return ResponseEntity.badRequest().body(mensaje);
        }
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", aulas);
        return ResponseEntity.ok(mensaje);
    }
}
