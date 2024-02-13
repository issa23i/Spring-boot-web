package com.springsimplespasos.universidad.universidadbackend;

import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Alumno;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Carrera;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Direccion;
import com.springsimplespasos.universidad.universidadbackend.modelo.entidades.Persona;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.AlumnoDAO;
import com.springsimplespasos.universidad.universidadbackend.servicios.contratos.CarreraDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class UniversidadBackendApplication {

	@Autowired
	private AlumnoDAO servicioAlumno;
	@Autowired
	private CarreraDAO servicioCarrera;


	public static void main(String[] args) {
		String[] beanDefinitionNames = SpringApplication.run(UniversidadBackendApplication.class, args).getBeanDefinitionNames();
		/*for(String str : beanDefinitionNames){
			System.out.println(str);
		}*/
	}

	@Bean
	public CommandLineRunner runner(){
		return args -> {
			/*Direccion direccion = new Direccion("Ave del paraíso", "7", "44444", "Departamento floritura", "Piso 4º", "Localidad x");
			Persona alumno = new Alumno(null,"Pepe", "Cuerdas", "99999799R", direccion);

			Persona save = servicioAlumno.save(alumno);
			System.out.println(save.toString());

//			List<Persona> alumnos = (List<Persona>) servicio.findAll();
//			alumnos.forEach(System.out::println);

			Direccion direccion2 = new Direccion("Calle del Alma", "11", "44444", "Depart. 127", "11", "Sevilla");
			Persona alumno2 = new Alumno(null,"Andonio", "Maestre", "11110111H", direccion);

			Set<Alumno> alumnos = new HashSet<>();
			alumnos.add((Alumno) alumno);
			alumnos.add((Alumno) alumno2);

			Persona save0 = servicioAlumno.save(alumno2);
			System.out.println(save0.toString());

			Carrera carrera1 = new Carrera(null,"Filología",65,4);
			Carrera carrera2 = new Carrera(null,"Matemáticas",79,5);
			Carrera carrera3 = new Carrera(null,"Medicina",78,6);

			Carrera save2 = servicioCarrera.save(carrera2);
			Carrera save3 = servicioCarrera.save(carrera3);

			System.out.println(save2.toString());
			System.out.println(save3.toString());
			Optional<Persona> alumno3 = servicioAlumno.findById(3);
			Optional<Persona> alumno4 = servicioAlumno.findById(4);

			Set<Alumno> alumnos1 = new HashSet<>();
            alumno3.ifPresent(persona -> alumnos1.add((Alumno) persona));
            alumno4.ifPresent(persona -> alumnos1.add((Alumno) persona));

			Optional<Carrera> carrera4 = servicioCarrera.findById(2);
			carrera4.get().setAlumnos(alumnos1);
			Carrera save1 = servicioCarrera.save(carrera4.get());
			System.out.println(save1.toString());

			Optional<Carrera> carrera9 = servicioCarrera.findById(9);
			carrera9.get().setCantidadAnios(7);
			servicioCarrera.save(carrera9.get());*/

			Iterable<Carrera> carreras =  servicioCarrera.findCarrerasByNombreContains("jiii");
			carreras.forEach(carrera -> servicioCarrera.deleteById(carrera.getId()));

		};
	}

}
