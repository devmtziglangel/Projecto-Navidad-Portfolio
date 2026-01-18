package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.model.Skill;
import com.portfolio.my_portfolio_backend.repository.ISkillRepository;
import com.portfolio.my_portfolio_backend.repository.SkillRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
        * @ExtendWith(MockitoExtension.class)
 * Esta anotación le dice a JUnit 5 que use Mockito para procesar este test.
 * Es lo que permite que @Mock y @InjectMocks funcionen sin levantar todo Spring Boot.
        * Hace que el test sea ultra rápido (milisegundos) porque no usa base de datos real.
        */
@ExtendWith(MockitoExtension.class)
public class SkillServiceImplTest {




    /**
     * @Mock
     * Crea un "doble" o simulacro de la interfaz.
     * No tiene lógica real; nosotros le diremos qué debe responder
     * cuando el servicio le pida algo (usando when/thenReturn).
     */
    @Mock
    private ISkillRepository skillRepository;

    /**
     * @InjectMocks
     * Crea una instancia REAL de la clase que queremos probar (SkillServiceImpl).
     * Mockito busca automáticamente todos los campos anotados con @Mock
     * y los "inyecta" (los mete dentro) de esta clase.
     */
    @InjectMocks
    private SkillServiceImpl skillService;

    /*
        Creamos el test para ver si nos retorna una lista de skills
     */
    @Test
    void testFindAllReturnsListOfSkills(){
        //1ºEtapa --> ARRANQUE                                //coge dos objtos  skills y empaquetalos en una lista
        List<Skill> mockSkills = Arrays.asList(new Skill(), new Skill());
        //aqui indicamos, cuando se llame al metodo findAll, retornas el mockito skills, no el skill de verdad
        when(skillRepository.findAll()).thenReturn(mockSkills);

        //2ªETAPA --> ACCION
        List<Skill>skills = skillService.findAll(); //el findAll retorna una Lista, por lo tanto hay que poner el tipo que retorna

        //3.ºETAPA --> ASSERT, evaluar los Skills
        assertNotNull(skills);
        assertEquals(2, skills.size()); //vamos a verificar que el resultado (de la etapa 2) es valido
        assertEquals(2, skills.size()); //ponemos 2, porque en el pas 1 creamos 2 new skill
        verify(skillRepository, times(1)).findAll(); // El "detective": Asegura que el código realmente consultó al repositorio
    }

    @Test
    void testFindByIdReturnsSkillWhenFound(){
        Long id = 1L;
        Skill skillsMock = new Skill();

        when(skillRepository.findById(id)).thenReturn(Optional.of(skillsMock));

        Optional<Skill> skillOptional = skillService.findById(id);

        assertTrue(skillOptional.isPresent());
        assertEquals(skillsMock, skillOptional.get());
        verify(skillRepository, times(1)).findById(id);



    }
// Test de Búsqueda Fallida (El Optional vacío)
    @Test
    void testFindByIdReturnsEmptyWhenNotFound() {
        // ARRANGE
        Long id = 99L;
        // Simulamos que el repositorio devuelve una "caja vacía"
        when(skillRepository.findById(id)).thenReturn(Optional.empty());

        // ACT
        Optional<Skill> result = skillService.findById(id);

        // ASSERT
        assertFalse(result.isPresent(), "El Optional debería estar vacío");
        verify(skillRepository, times(1)).findById(id);
    }

// Test de Eliminación (void)
    @Test
    void testDeleteById() {
        // ARRANGE
        Long id = 1L;
        // No necesitamos un 'when' porque el método delete del repo suele ser void.
        // Solo queremos verificar que la orden "llegó" al repo.

        // ACT
        skillService.deleteById(id);

        // ASSERT / VERIFY
        // Verificamos que el servicio dio la orden de borrar al repo exactamente una vez
        verify(skillRepository, times(1)).deleteById(id);
    }
//TES DE GUARDADO
    @Test
    void testSaveSkill() {
        // ARRANGE
        Skill skillParaGuardar = new Skill(null, "Angular", 85, "fa-angular", 1L);
        Skill skillGuardadaConId = new Skill(1L, "Angular", 85, "fa-angular", 1L);

        // Entrenamos al mock para que "asigne" el ID 1 al guardar
        when(skillRepository.save(any(Skill.class))).thenReturn(skillGuardadaConId);

        // ACT
        Skill result = skillService.save(skillParaGuardar);

        // ASSERT
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        assertEquals("Angular", result.getName());
        verify(skillRepository, times(1)).save(skillParaGuardar);
    }




}
