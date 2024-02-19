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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/tipoempleado/{tipoEmpleado}")
    public ResponseEntity<?> findEmpleadoByTipoEmpleado(@PathVariable String tipoEmpleado){
        Map<String, Object> mensaje = new HashMap<>();
        
        Iterable<Persona> empleados = ((EmpleadoDAOImpl)service).findEmpleadoByTipoEmpleado(tipoEmpleado);
        List<Persona> listaEmpleados = (List<Persona>) empleados;
        if(listaEmpleados.isEmpty()) {
            //throw new BadRequestException(String.format("No se encontraron %s por tipo de empleado %s",nombreEntidad.toLowerCase(),tipoEmpleado));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("No se encontraron %s por tipo de empleado %s",nombreEntidad.toLowerCase(),tipoEmpleado));
            return ResponseEntity.badRequest().body(mensaje);
        }
        //return empleados;
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", empleados);
        return ResponseEntity.ok(mensaje);
    }


    @PutMapping("/{id}/pabellon/{idPabellon}")
    public ResponseEntity<?> asignarPabellonAEmpleado(@PathVariable Integer id, @PathVariable Integer idPabellon){
        Map<String, Object> mensaje = new HashMap<>();
        Optional<Persona> optionalEmpleado = service.findById(id);
        if(optionalEmpleado.isEmpty()) {
            //throw new BadRequestException(String.format("Empleado con id %d no existe", id));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Empleado con id %d no existe", id));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Optional<Pabellon> optionalPabellon = pabellonDAO.findById(idPabellon);
        if(optionalPabellon.isEmpty()) {
            //throw new BadRequestException(String.format("Pabellon con id %d no existe", idPabellon));
            mensaje.put("success", Boolean.FALSE);
            mensaje.put("mensaje", String.format("Pabellon con id %d no existe", idPabellon));
            return ResponseEntity.badRequest().body(mensaje);
        }
        Empleado empleado = (Empleado) optionalEmpleado.get();
        Pabellon pabellon = optionalPabellon.get();
        empleado.setPabellon(pabellon);
        //return service.save(empleado);
        mensaje.put("success", Boolean.TRUE);
        mensaje.put("datos", service.save(empleado));
        return ResponseEntity.ok(mensaje);
    }

}
