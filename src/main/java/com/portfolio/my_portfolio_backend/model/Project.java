package com.portfolio.my_portfolio_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashcode automáticamente
@AllArgsConstructor // Genera constructor con todos los atributos
@NoArgsConstructor // Genera constructor vacío (obligatorio para Spring y RowMapper)

public class Project {

    private Long id;
    private String title;
    private String description;
    private String imageUrl ; //ruta a la imagen del proyecto
    private String projectUrl; //url del proyecto desplegado o Github
    private Long personalInfoID; //fk a personalInfo

}
