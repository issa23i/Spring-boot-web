package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/pabellones")
public class PabellonController extends GenericController<Pabellon, PabellonDAO> {

    private final AulaDAO aulaDAO;
    @Autowired
    public PabellonController(PabellonDAO service, AulaDAO aulaDAO) {
        super(service);
        this.aulaDAO = aulaDAO;
        nombreEntidad = "Pabellon";
    }

    @PutMapping("/{idPabellon}/aula/{idAula}")
    public ResponseEntity<?> asignarAulaAPabellon(@PathVariable Integer idPabellon, @PathVariable Integer idAula){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Pabellon> optionalPabellon = service.findById(idPabellon);
        if(optionalPabellon.isEmpty())  {
            //throw new BadRequestException(String.format("Pabellon con id %d no existe", idPabellon));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Pabellon con id %d no existe", idPabellon));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Optional<Aula> optionalAula= aulaDAO.findById(idAula);
        if(optionalAula.isEmpty())  {
            //throw new BadRequestException(String.format("Aula con id %d no existe", idAula));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Aula con id %d no existe", idAula));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Aula aula = optionalAula.get();
        Pabellon pabellon = optionalPabellon.get();
        Set<Aula> aulas = pabellon.getAulas();
        if(aulas.isEmpty()) aulas = new HashSet<>();
        aulas.add(aula);
        pabellon.setAulas(aulas);
        //return service.save(pabellon);
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", service.save(pabellon));
        return ResponseEntity.ok(mensaje);
    }



}
