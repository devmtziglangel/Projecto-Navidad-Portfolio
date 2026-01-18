package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.exception.ValidationException;
import com.portfolio.my_portfolio_backend.model.Education;
import com.portfolio.my_portfolio_backend.model.Experience;
import com.portfolio.my_portfolio_backend.repository.IExperienceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ExperienceServiceTest {

    @Autowired
    private IExperienceService experienceService;

    @Autowired
    private IExperienceRepository experienceRepository;

    @Test
    void testValidSaveExperience() {
        // 1. CAMINO FELIZ: Datos correctos
        Experience validExperience = new Experience(
                null,
                "Senior Java Developer",
                "Tech Solutions S.A.",
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2023, 5, 1),
                "Desarrollo de microservicios con Spring Boot",
                1L
        );

        // ACCIÓN
        Experience savedExperience = experienceService.save(validExperience);

        // VERIFICACIÓN: Comprobamos que el servicio y la DB respondieron
        assertNotNull(savedExperience.getId(), "La base de datos debe asignar un ID");

        assertNotNull(experienceRepository
                .findById(savedExperience.getId())
                .orElse(null), "El objeto guardado debe existir en la BBDD");
    }

    @Test
    void testSaveInvalidExperience() {
        // 2. CAMINO TRISTE: Fecha de inicio en el futuro (Invalida @PastOrPresent)
        Experience invalidExperience = new Experience(
                null,
                "Astronauta",
                "NASA",
                LocalDate.now().plusYears(10), // 2036 no es pasado ni presente
                null,
                "Exploración de Marte",
                1L
        );

        // VERIFICACIÓN: Esperamos que la seguridad del Servicio detenga el proceso
        assertThrows(ValidationException.class, () -> {
            experienceService.save(invalidExperience);
        }, "Debe lanzar ValidationException porque la fecha de inicio es futura");
    }
}

