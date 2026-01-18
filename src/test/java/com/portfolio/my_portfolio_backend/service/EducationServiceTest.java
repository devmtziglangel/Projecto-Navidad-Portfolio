package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.exception.ValidationException;
import com.portfolio.my_portfolio_backend.model.Education;
import com.portfolio.my_portfolio_backend.repository.IEducationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext (classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class EducationServiceTest {

    @Autowired
    private IEducationService educationService;

    @Autowired
    private IEducationRepository educationRepository;

    @Test
    void testValidSaveEducation(){
        // 1. CAMINO FELIZ: Datos correctos y coherentes
        Education validEducation = new Education(
                null,
                "Ingeniería en Sistemas",
                "Universidad de Santiago",
                LocalDate.of(2015, 9, 1),
                LocalDate.of(2020, 6, 30),
                "Graduado con honores",
                1L
        );

        // 2. ACCIÓN: Guardamos a través del servicio
        Education saveEducation = educationService.save(validEducation);

        //3. VERIFICACIÓN: Comprobamos ID y persistencia

        assertNotNull(saveEducation.getId(), " La base de datos debe asignar un ID a la educación");
        assertNotNull(educationRepository.
                findById(saveEducation.getId()).
                orElse(null), "El registro debe existir en la BBDD");

    }

    @Test
    void testSaveInvalides(){
        Education invalidEducation =  new Education(
                null,
                "Master en IA",
                "MIT",
                LocalDate.now().plusYears(1), // Fecha futura
                null,
                "En curso",
                1L

        );

        //QUE SALTE EL THROWS
        assertThrows(ValidationException.class, () -> {
            educationService.save(invalidEducation);


        }, "Debe fallar al intentar guardar una fecha de inicio futura");


    }


}
