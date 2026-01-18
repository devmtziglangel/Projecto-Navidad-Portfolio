package com.portfolio.my_portfolio_backend.model;



import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashcode automáticamente
@AllArgsConstructor // Genera constructor con todos los atributos
@NoArgsConstructor // Genera constructor vacío (obligatorio para Spring y RowMapper)

public class Experience {
    private Long id;

    //LAS VALIDACIONES AHORA EN EL DTO

//    @NotBlank(message = "El título no puede estar vacío")
    private String jobTitle;

//    @NotBlank(message = "Debes poner el nombre de la empresa / compañía")
    private String companyName;

    // CAMBIO: @NotNull en lugar de @NotBlank para fechas
//    @NotNull(message = "La fecha de inicio es obligatoria")
//    @PastOrPresent(message = "La fecha de inicio no puede ser futura")
    private LocalDate startDate;

    // Para la fecha de fin no solemos poner @NotNull por si el usuario sigue trabajando ahí
//    @PastOrPresent(message = "La fecha de fin no puede ser futura")
    private LocalDate endDate;

//    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    private Long personalInfoID; // FK a PersonalInfo
}
