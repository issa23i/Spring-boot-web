package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AulaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController extends GenericController<Aula, AulaDAO> {
    @Autowired
    public AulaController(AulaDAO service) {
        super(service);
        nombreEntidad = "Aula";
    }

    @GetMapping("/pizarron/{pizarron}")
    Iterable<Aula> findAulasByPizarron(@PathVariable String pizarron){
        Iterable<Aula> aulas = service.findAulasByPizarron(pizarron);
        List<Aula> aulaList = (List<Aula>) aulas;
        if(aulaList.isEmpty()) throw new BadRequestException(String.format("No se encontraron %s por tipo de pizarrón %s",nombreEntidad.toLowerCase(),pizarron.toLowerCase()));
        return aulas;
    }
}
