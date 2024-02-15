package com.springsimplespasos.universidad.universidadbackend.controlador;

import com.springsimplespasos.universidad.universidadbackend.exceptions.BadRequestException;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.TipoEmpleado;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.PersonaDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones.EmpleadoDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController extends PersonaController{
    @Autowired
    public EmpleadoController(@Qualifier("empleadoDAOImpl")PersonaDAO empleadoDAO) {
        super(empleadoDAO);
        nombreEntidad = "Empleado";
    }

    @GetMapping("/{tipoEmpleado}")
    public Iterable<Persona> findEmpleadoByTipoEmpleado(String tipoEmpleado){
        
        Iterable<Persona> empleados = ((EmpleadoDAOImpl)service).findEmpleadoByTipoEmpleado(tipoEmpleado);
        List<Persona> listaEmpleados = (List<Persona>) empleados;
        if(listaEmpleados.isEmpty()) throw new BadRequestException(String.format("No se encontraron %s por tipo de empleado %s",nombreEntidad.toLowerCase(),tipoEmpleado));
        return empleados;
    }

}
