package com.springsimplespasos.universidad.universidadbackend.modelo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "profesores")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Profesor extends Persona {

    @Column
    private BigDecimal sueldo;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "profesor_carrera",
            joinColumns = @JoinColumn(name = "profesor_id"),
            inverseJoinColumns = @JoinColumn(name = "carrera_id")
    )
    private Set<Carrera> carreras;

    public Profesor() {
    }

    public Profesor(Integer id, String nombre, String apellido, String dni, Direccion direccion, BigDecimal sueldo) {
        super(id, nombre, apellido, dni, direccion);
        this.sueldo = sueldo;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public Set<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(Set<Carrera> carreras) {
        this.carreras = carreras;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\tProfesor{" +
                "sueldo=" + sueldo +
                '}';
    }
}
