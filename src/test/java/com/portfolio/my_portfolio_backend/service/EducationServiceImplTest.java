package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.model.Education;
import com.portfolio.my_portfolio_backend.repository.IEducationRepository;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)

public class EducationServiceImplTest {

    @Mock
    private IEducationRepository educationRepository;

    @InjectMocks
    private EducationServiceImpl educationService;

    //TEST findByALL
    @Test
    void testFindAllReturnsListOfEducation(){

        //1. ARRANQUE
        List<Education> mockEducations = Arrays.asList(new Education(), new Education());

        //2.ACCION
        List<Education> result = educationService.findAll();

        //3.ASSERTS

        assertNotNull(result);
        assertEquals(2, result.size());

        //4 VERFICACION

        verify(educationRepository, times(1)).findAll();

    }
    //TEST Save
    @Test
    void testSaveEducation() {
        // 1. ARRANQUE (Preparamos los "actores")
        Education nuevaEdu = new Education();
        Education eduConId = new Education();
        // (Imagina que a eduConId le hemos puesto un ID manualmente para el simulacro)

        // 2. EL ENTRENAMIENTO (El famoso WHEN)
        // "Cuando al repo le digan 'guarda esta edu', él debe devolver la 'edu con ID'"
        when(educationRepository.save(nuevaEdu)).thenReturn(eduConId);

        // 3. ACCIÓN
        Education resultado = educationService.save(nuevaEdu);

        // 4. ASSERT
        assertNotNull(resultado);
        // Aquí comprobaríamos que el resultado tiene el ID que pusimos en eduConId
    }

    //TEST FINBYID
    @Test
    void testFindByIDReturnsEducationWhenFound(){
        //LOS ACTORES
        Long idExistente = 1L;
        Education educationMock = new Education();
        Optional<Education> educationOptional = educationService.findById(idExistente);


        //EL GUION
        when(educationRepository.findById(idExistente)).thenReturn(Optional.of(educationMock));

        //VALIDACION ASSERTS
        assertTrue(educationOptional.isPresent());
        assertEquals(educationMock, educationOptional.get());
        verify(educationRepository, times(1)).findById(idExistente);
    }

    //TEST BORRAR --> es especial, porque borramos, no vlaidamos con ,los Asetrts
    @Test
    void testDeleteEducation(){
        Long idBorrar = 1L;

        educationService.deleteById(idBorrar);

        verify(educationRepository, times(1)).deleteById(idBorrar);
    }



}
