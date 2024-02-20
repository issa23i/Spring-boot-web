package com.springsimplespasos.universidad.universidadbackend.modelo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Carrera de la universidad", value = "Carrera", reference = "Carrera")
public class CarreraDTO {
    @ApiModelProperty(name = "Código del sistema", example = "5")
    private Integer codigo;
    @NotNull
    @NotEmpty(message = "Debe de ingresar un valor")
    @Size(min = 1, max = 150)
    @ApiModelProperty(name = "Nombre de la carrera", example = "Historia del Arte", required = true)
    private String nombre;
    @Positive(message = "El valor no puede ser negativo")
    @ApiModelProperty(name = "Cantidad de materias", example = "55", required = true, notes = "Cantidad de materias de toda la carrera")
    private Integer cantidad_materias;
    @Positive(message = "El valor no puede ser negativo")
    @ApiModelProperty(name = "Cantidad de años de duración de la carrera", example = "4", required = true)
    private Integer camtidad_anyos;
}
