package com.springsimplespasos.universidad.universidadbackend.repositorios;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.enumeradores.Pizarron;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AulaRepository extends CrudRepository<Aula,Integer> {
   // @Query("Select a from Aula a where UPPER(a.pizarron) = UPPER(?1)")
    Iterable<Aula> findAulasByPizarron(Pizarron pizarron);
}
