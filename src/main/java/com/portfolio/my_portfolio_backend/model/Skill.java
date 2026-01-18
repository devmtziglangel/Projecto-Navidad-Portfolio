package com.portfolio.my_portfolio_backend.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashcode automáticamente
@AllArgsConstructor // Genera constructor con todos los atributos
@NoArgsConstructor // Genera constructor vacío (obligatorio para Spring y RowMapper)
public class Skill {
    private Long id;


    // LAS VALIDACINES AHORA SE HACEN EN EL DTO

//    @NotBlank (message = "El nombre de la habilidad no puede estar vacio")

    private String name; // EJ -> JAVA, HTML, CSS, SQL...

//    @NotNull(message = "El porcentaje no puede ser nulo")
//    @Min(value = 0, message = "El porcentaje no puede ser inferior a 0")
//    @Max(value = 100, message = "El porcentaje no puede superar 100")
    private Integer levelPercentage; //EJ; 90, 85..Para barras de progreso

//    @NotBlank(message = "La clase del incono no puede estar vacia")
    private String iconClass; // EJ --> "fab fa-java" apra FontAwesome


    private Long personalInfoId; //FK a PersonalInfo
}

