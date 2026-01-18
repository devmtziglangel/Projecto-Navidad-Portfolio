package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.exception.ValidationException;
import com.portfolio.my_portfolio_backend.model.PersonalInfo;
import com.portfolio.my_portfolio_backend.repository.IPersonalInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonalInfoServiceTest {

    @Autowired
    private IPersonalInfoService personalInfoService;

    @Autowired
    private IPersonalInfoRepository personalInfoRepository;

    @Test
    void testValidPersonalInfo() {
        // 1. Camino Feliz: Datos correctos
        PersonalInfo validPersonalInfo = new PersonalInfo(
                null,                // id
                "Juan",              // firstName
                "García",            // lastName
                "Full Stack Java",   // title
                "Descripción larga sobre mi perfil...", // profileDescription
                "http://img.com/p.jpg", // profileImageUrl
                5,                   // yearsOfExperience
                "juan@email.com",    // email
                "+34 123456789",     // phone
                "linkedin.com/in/juan", // linkedinUrl
                "https://github.com/juan" // githubUrl
        );

        PersonalInfo savedPersonalInfo = personalInfoService.save(validPersonalInfo);

        // Verificamos que se generó el ID
        assertNotNull(savedPersonalInfo.getId(), "El objeto guardado debe tener un ID asignado");

        // Verificamos que realmente está en la base de datos
        assertNotNull(personalInfoRepository
                .findbyId(savedPersonalInfo.getId())
                .orElse(null), "El objeto guardado debe existir en la BBDD");
    }

    @Test
    void testSaveInvalid() {
        // 2. Camino Triste: Nombre vacío para forzar el error
        PersonalInfo invalidPersonalInfo = new PersonalInfo(
                null, "", "García", "Full Stack", "Desc...",
                null, 5, "juan@email.com", null, null, null
        );

        //Que salter el throws Validation
        //  Se añade coma y se cierran paréntesis correctamente
        assertThrows(ValidationException.class, () -> {
            personalInfoService.save(invalidPersonalInfo);
        }, "Debe lanzarse una ValidationException cuando el nombre de PersonalInfo esté vacío");
    }
}