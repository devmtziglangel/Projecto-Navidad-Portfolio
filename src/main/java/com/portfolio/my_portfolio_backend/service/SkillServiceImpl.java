package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.model.Skill;
import com.portfolio.my_portfolio_backend.repository.ISkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service // ES LO PRIMERO QUE TENEMOS QUE HACER, ANOTAR LA CLASE
@RequiredArgsConstructor
public class SkillServiceImpl implements ISkillService {

    private final ISkillRepository skillRepository;

    /*
     * =========================================================================
     * BLOQUE DE APUNTES: VALIDACIÓN MANUAL (REFERENCIA PARA FUTUROS PROYECTOS)
     * =========================================================================
     * * // 1. PREPARACIÓN DE LA VALIDACIÓN
     * // Creamos el contenedor 'result' donde el validador anotará cualquier fallo que encuentre
     * // BindingResult result = new BeanPropertyBindingResult(skill, "skill");
     *
     * // 2. EJECUCIÓN DEL VALIDADOR
     * // El motor revisa las anotaciones del Modelo (@NotBlank, @Min, @Max)
     * // validator.validate(skill, result);
     *
     * // 3. GESTIÓN DE ERRORES Y ATOMICIDAD
     * // Si hay errores, lanzamos nuestra excepción personalizada.
     * // Al estar dentro de un método @Transactional, si se lanza esta excepción,
     * // Spring garantiza que NO se toque la base de datos (Rollback).
     * // if(result.hasErrors()){
     * //     throw new ValidationException(result);
     * // }
     *
     * // COMPARATIVA CON EL MÉTODO ANTERIOR (Mantenido para referencia):
     * // Antes teníamos que escribir lógica manual por cada campo:
     * // if(skill.getLevelPercentage() < 0 || skill.getLevelPercentage() > 100){
     * //     throw new IllegalArgumentException("El porcentaje tiene que estar entre 0 y 100");
     * // }
     * // DESVENTAJA: Si el modelo tiene 20 campos, tendrías 20 'if' distintos.
     * // VENTAJA DEL NUEVO: El validator revisa TODOS los campos automáticamente
     * // basándose en las etiquetas del modelo Skill.
     * =========================================================================
     */

    @Override
    @Transactional
    public Skill save(Skill skill) {
        // 4. PERSISTENCIA
        // Si el código llega aquí, significa que los datos son 100% válidos.
        // Actualmente la validación se delega al DTO en el Controller.
        return skillRepository.save(skill);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }
    // CORRECCIÓN AQUÍ: El nombre debe ser EXACTAMENTE el que pide la interfaz
    @Override
    @Transactional(readOnly = true)
    public List<Skill> findByPersonalInfoId(Long personalInfoId) {
        return skillRepository.findByPersonalInfoId(personalInfoId);
    }

    @Override
    public List<Skill> findSkillsByPersonalInfo(Long personalInfoId) {
        return List.of();
    }
}