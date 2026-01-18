package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.model.Experience;
import com.portfolio.my_portfolio_backend.repository.IExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * APUNTES DE ARQUITECTURA:
 * @Service: Marcamos la clase como lógica de negocio.
 * @RequiredArgsConstructor: Genera el constructor para inyectar el repositorio.
 * NOTA: Ahora la validación se realiza en el DTO antes de llegar aquí.
 */
@Service
@RequiredArgsConstructor
public class ExperienceService implements IExperienceService {

    private final IExperienceRepository experienceRepository;
    // El Validator se retira de la inyección activa para limpiar el código,
    // pero se mantiene la lógica en los comentarios de abajo.

    @Override
    @Transactional(readOnly = true)
    public List<Experience> findAll() {
        return experienceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Experience> findById(Long id) {
        return experienceRepository.findById(id);
    }

    @Override
    @Transactional
    public Experience save(Experience experience) {
        /*
         * =========================================================================
         * BLOQUE DE APUNTES: VALIDACIÓN MANUAL (REFERENCIA PARA FUTUROS PROYECTOS)
         * Actualmente se valida con @Valid y DTO en el Controller.
         * -------------------------------------------------------------------------
         * // 1. PREPARAR LA VALIDACIÓN --> CREANDO LA MOCHILA RESULT
         * // BindingResult result = new BeanPropertyBindingResult(experience, "experience");
         * // validator.validate(experience, result);
         *
         * // Si hay errores, lanzamos nuestra excepción personalizada para activar el Rollback
         * // if (result.hasErrors()){
         * //      throw new ValidationException(result);
         * // }
         *
         * // --- REGLAS DE NEGOCIO ---
         * // Validación 1: Fecha de inicio obligatoria
         * // if (experience.getStartDate() == null) {
         * //     throw new IllegalArgumentException("La fecha de inicio no puede estar vacía.");
         * // }
         * // Validación 2: Fecha inicio no puede ser posterior a la de fin
         * // if (experience.getEndDate() != null && experience.getStartDate().isAfter(experience.getEndDate())) {
         * //     throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la de fin.");
         * // }
         * =========================================================================
         */

        // PERSISTENCIA: El objeto ya viene validado desde el Controller/DTO
        return experienceRepository.save(experience);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        // Log de depuración mantenido para tus apuntes
        System.out.println("Borrando experiencia por ID " + id + " en el servicio...");
        experienceRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Experience> findExperienceByPersonalInfoId(Long personalInfoId) {
        return experienceRepository.findByPersonalInfoId(personalInfoId);
    }
}