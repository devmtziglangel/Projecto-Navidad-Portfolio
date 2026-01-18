package com.portfolio.my_portfolio_backend.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInfo {

    //LAS VALIDACIONES AHORA EN EL DTO


    private Long id;

//    @NotBlank(message = "El nombre no puede estar vacío")
    private String firstName;

//    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

//    @NotBlank(message = "El título profesional es necesario")
    private String title;

//    @NotBlank(message = "La descripción de perfil es obligatoria")
    private String profileDescription;

    private String profileImageUrl;

//    @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
    private Integer yearsOfExperience;

//    @NotBlank(message = "El email es obligatorio")
//    @Email(message = "Debes proporcionar un formato correcto")
    private String email;

    private String phone;
    private String linkedinUrl;

//    @URL(message = "Debe ser una URL válida")
    private String githubUrl;
}