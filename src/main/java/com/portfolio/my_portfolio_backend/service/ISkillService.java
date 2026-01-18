package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.model.Skill;

import java.util.List;
import java.util.Optional;

public interface ISkillService {
    Skill save(Skill skill);
    Optional<Skill> findById(Long id);
    List<Skill> findAll();
    void deleteById(Long id);

    // El método extra que conecta Personas con Habilidades
    List<Skill> findByPersonalInfoId(Long personalInfoId);

    List<Skill> findSkillsByPersonalInfo(Long personalInfoId);
}