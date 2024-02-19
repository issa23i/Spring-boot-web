package com.springsimplespasos.universidad.universidadbackend.modelo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraDTO {
    private Integer codigo;
    @NotNull
    @NotEmpty(message = "Debe de ingresar un valor")
    @Size(min = 1, max = 150)
    private String nombre;
    @Positive(message = "El valor no puede ser negativo")
    private Integer cantidad_materias;
    @Positive(message = "El valor no puede ser negativo")
    private Integer camtidad_anyos;
}
