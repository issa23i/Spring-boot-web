package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Pabellon;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.TipoEmpleado;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PabellonDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones.EmpleadoDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController extends PersonaController{

    private final PabellonDAO pabellonDAO;
    @Autowired
    public EmpleadoController(@Qualifier("empleadoDAOImpl")PersonaDAO empleadoDAO, PabellonDAO pabellonDAO) {
        super(empleadoDAO);
        this.pabellonDAO = pabellonDAO;
        nombreEntidad = "Empleado";
    }

    @GetMapping("/{tipoEmpleado}")
    public Iterable<Persona> findEmpleadoByTipoEmpleado(String tipoEmpleado){
        
        Iterable<Persona> empleados = ((EmpleadoDAOImpl)service).findEmpleadoByTipoEmpleado(tipoEmpleado);
        List<Persona> listaEmpleados = (List<Persona>) empleados;
        if(listaEmpleados.isEmpty()) throw new BadRequestException(String.format("No se encontraron %s por tipo de empleado %s",nombreEntidad.toLowerCase(),tipoEmpleado));
        return empleados;
    }


    @PutMapping("/{id}/pabellon/{idPabellon}")
    public Persona asignarPabellonAEmpleado(@PathVariable Integer id, @PathVariable Integer idPabellon){
        Optional<Persona> optionalEmpleado = service.findById(id);
        if(optionalEmpleado.isEmpty()) throw new BadRequestException(String.format("Empleado con id %d no existe", id));
        Optional<Pabellon> optionalPabellon = pabellonDAO.findById(idPabellon);
        if(optionalPabellon.isEmpty()) throw new BadRequestException(String.format("Pabellon con id %d no existe", idPabellon));
        Empleado empleado = (Empleado) optionalEmpleado.get();
        Pabellon pabellon = optionalPabellon.get();
        empleado.setPabellon(pabellon);
        return service.save(empleado);
    }

}
