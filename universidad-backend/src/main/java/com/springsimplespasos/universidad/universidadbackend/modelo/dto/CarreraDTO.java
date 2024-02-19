package com.springsimplespasos.universidad.universidadbackend.modelo.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraDTO {
    private Integer codigo;
    private String nombre;
    private Integer cantidad_materias;
    private Integer camtidad_anyos;
}
