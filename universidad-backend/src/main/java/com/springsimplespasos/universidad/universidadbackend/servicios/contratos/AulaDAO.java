package com.springsimplespasos.universidad.universidadbackend.servicios.contratos;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Aula;

public interface AulaDAO extends GenericoDAO<Aula>{
    Iterable<Aula> findAulasByPizarron(String pizarron);
}
