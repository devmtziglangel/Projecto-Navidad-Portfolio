package com.portfolio.my_portfolio_backend.model;


import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashcode automáticamente
@AllArgsConstructor // Genera constructor con todos los atributos
@NoArgsConstructor // Genera constructor vacío (obligatorio para Spring y RowMapper)

public class Education {
    //LAS VALIDACIONES AHORA EN EL DTO

    private Long id;
//    @NotBlank(message = "El titulo no puede estar vacio ")
    private String degree; // EJ--> "Ingenieria en Ssitemas"

//    @NotBlank(message = "Tienes que indicar un lugar de estudio")
    private String institution; // EJ--> "universidad de Santiago de Compostela"

//    @NotBlank(message = "La fecha de inicio no puede ser nula")
//    @PastOrPresent(message = "La fecha de inicio no puede ser futura")
    private LocalDate startDate;

//    @PastOrPresent(message = "La fecha de fin no puede ser futura")

    private LocalDate endDate; //Puede ser null si esta en curso;

//    @NotBlank(message = "La descripcion no puede ser vacia ")
    private String description; //Breve Descripcion de logros o cursos

    //La validacion de la FK se maneja a Nivel Servicio
    private  Long personalInfoID; //FK a PersonalInfo
}
