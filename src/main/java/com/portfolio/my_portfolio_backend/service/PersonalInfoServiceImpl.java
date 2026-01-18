package com.portfolio.my_portfolio_backend.service;

import com.portfolio.my_portfolio_backend.exception.ValidationException;
import com.portfolio.my_portfolio_backend.model.PersonalInfo;
import com.portfolio.my_portfolio_backend.repository.IPersonalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import org.springframework.validation.Validator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalInfoServiceImpl implements IPersonalInfoService {

    private final IPersonalInfoRepository personalInfoRepository;
    private  final Validator validator;



    @Override
    @Transactional // le decimos a Spring que todo tiene que ejecutarse como una unidad atomica, sino rollback
    public PersonalInfo save(PersonalInfo personalInfo) {
        //  Creamos la "libreta de notas" para guardar posibles errores
        BindingResult result = new BeanPropertyBindingResult(personalInfo, "personalInfo");
        //  El validador revisa el objeto y anota los fallos en 'result'
        validator.validate(personalInfo, result);
        // Preguntamos: "¿Hay algo anotado en la libreta de errores?"
        if(result.hasErrors()){

            // Lanzamos una excepción para detener el proceso y que NO se guarde en la DB
            throw  new ValidationException(result);
        }

        return personalInfoRepository.save(personalInfo);
    }

    @Override
    @Transactional (readOnly = true)
    public Optional<PersonalInfo> findbyId(Long id) {
        return  personalInfoRepository.findbyId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalInfo> findAll() {
        return personalInfoRepository.findAll();
    }


    @Override
    @Transactional
    public void deleteById(Long id) {
        System.out.println("Eliminando ficha por ID" + id + " del servicio...");

        personalInfoRepository.deleteById(id);

    }
}
