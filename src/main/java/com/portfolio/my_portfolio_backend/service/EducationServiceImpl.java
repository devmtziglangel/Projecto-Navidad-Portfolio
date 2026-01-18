package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.model.Education;
import com.portfolio.my_portfolio_backend.repository.IEducationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * APUNTES DE ARQUITECTURA:
 * @Service: Marca la clase como componente de negocio en el contenedor de Spring.
 * Ahora la validación se delega a la capa de Controller mediante DTOs y @Valid.
 */
@Service
public class EducationServiceImpl implements IEducationService {

    private final IEducationRepository educationRepository;

    // Quitamos el Validator de los atributos activos para que el código esté limpio.
    public EducationServiceImpl(IEducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Education> findAll() {
        return educationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Education> findById(Long id) {
        return educationRepository.findById(id);
    }

    @Override
    @Transactional
    public Education save(Education education) {
        /*
         * =========================================================================
         * BLOQUE DE APUNTES: VALIDACIÓN MANUAL (REFERENCIA PARA FUTUROS PROYECTOS)
         * Actualmente el código es directo porque se valida con DTO en el Controller.
         * -------------------------------------------------------------------------
         * // 1. Preparamos la validación
         * // Crear el contenedor "result", para que el validador anote el fallo
         * // BindingResult result = new BeanPropertyBindingResult(education, "education");
         * // validator.validate(education, result);
         *
         * // 2. Asignar errores y Atomicidad
         * // Si hay errores, lanzamos nuestra excepción personalizada
         * // if(result.hasErrors()){
         * //     throw new ValidationException(result);
         * // }
         *
         * // REFERENCIA DE LÓGICA MANUAL:
         * // if (education.getStartDate() == null) {
         * //     throw new IllegalArgumentException("La fecha de inicio no puede estar vacía.");
         * // }
         * =========================================================================
         */

        // PERSISTENCIA: Los datos llegan aquí ya validados por el DTO
        return educationRepository.save(education);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Mantenemos tu log de consola para depuración
        System.out.println("Eliminando educacion por ID " + id + " en el servicio...");
        educationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Education> findEducationByPersonalInfoId(Long personalInfoId) {
        return educationRepository.findByPersonalInfoId(personalInfoId);
    }
}