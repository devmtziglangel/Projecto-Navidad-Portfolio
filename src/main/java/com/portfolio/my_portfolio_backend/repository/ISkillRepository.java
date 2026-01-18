package com.portfolio.my_portfolio_backend.repository;

import com.portfolio.my_portfolio_backend.model.Skill; // Solo necesitas este modelo
import java.util.List;
import java.util.Optional;

public interface ISkillRepository {

    // Guardar o actualizar una habilidad
    Skill save(Skill skill);

    // Buscar una habilidad específica por su propio ID
    Optional<Skill> findById(Long id); // Cambiado: Optional<Skill>

    // Listar todas las habilidades que existen
    List<Skill> findAll(); // Cambiado: List<Skill>

    // Borrar una habilidad
    void deleteById(Long id);

    // Este es excelente: buscar todas las habilidades de una persona específica
    List<Skill> findByPersonalInfoId(Long personalInfoId);
}