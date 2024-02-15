package com.springsimplespasos.universidad.universidadbackend.repositorios;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("repositorioEmpleados")
public interface EmpleadoRepository extends PersonaRepository{
    @Query("Select e from Empleado e where UPPER(e.tipoEmpleado) = UPPER(?1)")
    Iterable<Persona> findEmpleadoByTipoEmpleado(String tipoEmpleado);
}
