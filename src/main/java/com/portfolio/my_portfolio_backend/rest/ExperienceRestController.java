package com.portfolio.my_portfolio_backend.rest; // Cambié 'rest' por 'controller' por convención

import com.portfolio.my_portfolio_backend.model.Experience;
import com.portfolio.my_portfolio_backend.service.IExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/experiences") // Sugerido plural para coincidir con tus pruebas
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Importante para conectar con tu futuro Frontend
public class ExperienceRestController {

    private final IExperienceService experienceService;

    @GetMapping
    public List<Experience> findAll() {
        return experienceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Experience>finById(@PathVariable Long id){
        Optional<Experience> experience = experienceService.findById(id);
        if(experience.isPresent()){
            return new ResponseEntity<>(experience.get(), HttpStatus.OK);
        }else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Corregido: @RequestBody en lugar de @PathVariable
    @PostMapping
    public Experience save(@RequestBody Experience experience) {
        return experienceService.save(experience);
    }

    // ACTUALIZAR REGISTRO EXISTENTE
    @PutMapping("/{id}")
    public Experience update(@PathVariable Long id, @RequestBody Experience experience) {
        experience.setId(id);
        return experienceService.save(experience);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        experienceService.deleteById(id);
    }

    // LA RUTA QUE FALTABA: Obtener experiencias por ID de persona
    @GetMapping("/person/{personalInfoId}")
    public List<Experience> findByPersonalInfoId(@PathVariable Long personalInfoId) {
        return experienceService.findExperienceByPersonalInfoId(personalInfoId);
    }
}