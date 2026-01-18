package com.portfolio.my_portfolio_backend.rest;

import com.portfolio.my_portfolio_backend.model.PersonalInfo;
import com.portfolio.my_portfolio_backend.repository.IPersonalInfoRepository;
import com.portfolio.my_portfolio_backend.service.IPersonalInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController // a diferencia de un Controller, el Rest retorna un json
@RequestMapping("/api/test-personal-info")
public class PersonalInfoTestController {

    private final IPersonalInfoService personalInfoService;

    public PersonalInfoTestController(IPersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }

    @GetMapping("/all")
    public List<PersonalInfo>getAllPersonalInfo(){

        return personalInfoService.findAll();

    }

    @GetMapping("/{id}")
    public PersonalInfo getPersonalInfoById(@PathVariable Long id){
        Optional<PersonalInfo> info =  personalInfoService.findbyId(id);
        if(info.isPresent()){
            return info.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Informacion personal no disponible en el Id: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<PersonalInfo> createPersonalInfo(@RequestBody PersonalInfo personalInfo) {
        // Usamos el objeto que nos llega por el parámetro, no uno nuevo vacío
        PersonalInfo newPersonalInfo = personalInfoService.save(personalInfo);

        // Devolvemos el objeto creado y un código 201 (CREATED)
        return new ResponseEntity<>(newPersonalInfo, HttpStatus.CREATED);
    }
}
