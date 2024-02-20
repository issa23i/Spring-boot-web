package com.springsimplespasos.universidad.universidadbackend.modelo.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Direccion;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Empleado;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Profesor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AlumnoDTO.class, name = "alumno"),
        @JsonSubTypes.Type(value = ProfesorDTO.class, name = "profesor"),
        @JsonSubTypes.Type(value = EmpleadoDTO.class, name = "empleado")
})
@ApiModel(description = "Persona", value = "Persona", reference = "Persona")
public abstract class PersonaDTO {
    @ApiModelProperty(name = "Código del sistema", example = "5")
    private Integer id;
    @ApiModelProperty(name = "Nombre de la persona", example = "Andrés", required = true)
    private String nombre;
    @ApiModelProperty(name = "Apellido de la persona", example = "Buenafuente", required = true)
    private String apellido;
    @Pattern(regexp = "\\d{8}[A-Z]")
    @Size(min = 8,max = 10)
    @ApiModelProperty(name = "NIF de la persona", example = "11111111H", required = true)
    private String dni;
    @ApiModelProperty(name = "Dirección de la persona", example = "Calle principal, 52")
    private Direccion direccion;

}
