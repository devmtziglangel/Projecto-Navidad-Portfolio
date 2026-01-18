package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.exception.ValidationException;
import com.portfolio.my_portfolio_backend.model.Skill;
import com.portfolio.my_portfolio_backend.repository.ISkillRepository;
import com.portfolio.my_portfolio_backend.repository.SkillRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest // Levanta todo el contexto de Spring (Base de datos, servicios, etc.) para la prueba
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //Cada vez que se hace una prueba, un test,
                                                                              // limpia la BBDD, para que no se contamine, y ejecuta otra prueba.
public class SkillServiceTest {

    @Autowired // Spring busca el servicio real y lo inyecta aquí
    private ISkillService skillService;

    @Autowired // Spring busca el repositorio para que podamos consultar la base de datos
    private ISkillRepository skillRepository;

    @Test
    void testSaveValidSkill() {
        // 1. PREPARACIÓN: Creamos un objeto con datos correctos.
        // El ID es 'null' porque aún no existe en la base de datos.
        Skill validSkill = new Skill(null, "Java", 90, "fab fa-java", 1L);

        // 2. ACCIÓN: Enviamos el objeto al servicio para que lo guarde.
        // Capturamos el resultado en 'savedSkill'.
        // Este nuevo objeto es IGUAL al anterior, pero ahora YA TIENE el ID que le puso la BBDD.
        Skill savedSkill = skillService.save(validSkill);

        // 3. VERIFICACIÓN A: ¿La base de datos le asignó un número de identidad?
        // Si el ID sigue siendo null, algo falló en el guardado.
        assertNotNull(savedSkill.getId(), "El objeto guardado debe tener un ID asignado");

        // 4. VERIFICACIÓN B: ¿El objeto está realmente dentro de la base de datos?
        // Usamos el repositorio para buscar por el ID que acabamos de recibir.
        // Si findById no encuentra nada, devuelve null y el test falla.
        assertNotNull(skillRepository
                .findById(savedSkill.getId())
                .orElse(null), "El objeto guardado debe existir en la BBDD");
    }
    @Test
    void testSaveInvalid() {
        // 1. PREPARACIÓN: Creamos una Skill con un error intencionado.
        // El nombre está vacío (""), lo que rompe la regla @NotBlank definida en el Modelo Skill.
        Skill invalidSkill = new Skill(null, "", 90, "fab fa-java", 1L);

        // 2. ACCIÓN Y VERIFICACIÓN (Todo en uno):
        // Usamos 'assertThrows' para decirle a JUnit: "Espero que ocurra un choque".
        // - El primer parámetro es la clase de la excepción que ESPERAMOS que salte (ValidationException).
        // - El segundo es una función lambda () -> que ejecuta el método que debería fallar.
        assertThrows(ValidationException.class, () -> {
            skillService.save(invalidSkill);
        }, "Debe lanzarse una ValidationException cuando el nombre de la Skill está vacío");

        // NOTA: Si el servicio NO lanza la excepción y guarda el nombre vacío,
        // el test fallará y se pondrá en ROJO, avisándonos de un fallo de seguridad.
    }
}

