package com.portfolio.my_portfolio_backend.rest;

import com.portfolio.my_portfolio_backend.model.Skill;
import com.portfolio.my_portfolio_backend.service.ISkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skills") // Plural para mantener el estándar
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SkillRestController {

    private final ISkillService skillService;

    // 1. Obtener todas las habilidades
    @GetMapping
    public List<Skill> findAll() {
        return skillService.findAll();
    }

    // 2. Obtener una habilidad por ID con respuesta HTTP adecuada
    @GetMapping("/{id}")
    public ResponseEntity<Skill> findById(@PathVariable Long id) {
        Optional<Skill> skill = skillService.findById(id);
        if (skill.isPresent()) {
            return new ResponseEntity<>(skill.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 3. Crear una nueva habilidad
    @PostMapping
    public Skill save(@RequestBody Skill skill) {
        return skillService.save(skill);
    }

    // 4. Actualizar una habilidad existente
    @PutMapping("/{id}")
    public Skill update(@PathVariable Long id, @RequestBody Skill skill) {
        skill.setId(id);
        return skillService.save(skill);
    }

    // 5. Eliminar una habilidad
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        skillService.deleteById(id);
    }

    // 6. Obtener habilidades por ID de persona
    @GetMapping("/person/{personalInfoId}")
    public List<Skill> findByPersonalInfoId(@PathVariable Long personalInfoId) {
        return skillService.findByPersonalInfoId(personalInfoId);
    }
}