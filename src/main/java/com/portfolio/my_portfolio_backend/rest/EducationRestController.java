package com.portfolio.my_portfolio_backend.rest;

import com.portfolio.my_portfolio_backend.model.Education;
import com.portfolio.my_portfolio_backend.service.IEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/educations") // Plural para consistencia
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite conexión con el Frontend
public class EducationRestController {

    private final IEducationService educationService;

    // 1. Obtener todos los registros
    @GetMapping
    public List<Education> findAll() {
        return educationService.findAll();
    }

    // 2. Obtener por ID con manejo de respuesta HTTP
    @GetMapping("/{id}")
    public ResponseEntity<Education> findById(@PathVariable Long id) {
        Optional<Education> education = educationService.findById(id);
        if (education.isPresent()) {
            return new ResponseEntity<>(education.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 3. Crear nuevo registro
    @PostMapping
    public Education save(@RequestBody Education education) {
        return educationService.save(education);
    }

    // 4. Actualizar registro existente
    @PutMapping("/{id}")
    public Education update(@PathVariable Long id, @RequestBody Education education) {
        education.setId(id);
        return educationService.save(education);
    }

    // 5. Eliminar por ID
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        educationService.deleteById(id);
    }

    // 6. Obtener educación por ID de persona (Ruta clave para el Portfolio)
    @GetMapping("/person/{personalInfoId}")
    public List<Education> findByPersonalInfoId(@PathVariable Long personalInfoId) {
        return educationService.findEducationByPersonalInfoId(personalInfoId);
    }
}