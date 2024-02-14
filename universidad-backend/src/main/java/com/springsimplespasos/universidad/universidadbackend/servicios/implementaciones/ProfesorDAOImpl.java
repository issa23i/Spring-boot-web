package com.springsimplespasos.universidad.universidadbackend.servicios.implementaciones;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.repositorios.PersonaRepository;
import com.springsimplespasos.universidad.universidadbackend.repositorios.ProfesorRepository;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.ProfesorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfesorDAOImpl extends PersonaDAOImpl implements ProfesorDAO {
    @Autowired
    public ProfesorDAOImpl(@Qualifier("repositorioProfesores")PersonaRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(readOnly =true)
    public Iterable<Persona> buscarProfesorPorCarrera(Integer idCarrera) {
        return ((ProfesorRepository)repository).findProfesorByCarreraId(idCarrera);
    }
}
