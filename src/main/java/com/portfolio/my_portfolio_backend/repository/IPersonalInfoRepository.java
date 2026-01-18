package com.portfolio.my_portfolio_backend.repository;

import com.portfolio.my_portfolio_backend.model.PersonalInfo;

import java.util.List;
import java.util.Optional;

public interface IPersonalInfoRepository {

    PersonalInfo save(PersonalInfo personalInfo); // Sirve tanto para Crear un nuevo perfil como para Actualizar el que ya tienes (Angel Iglesias).

    Optional<PersonalInfo> findbyId(Long id); // Te dará la lista de todos los perfiles. Útil si en el futuro quieres tener varios usuarios.

    List<PersonalInfo> findAll();

    void deleteById(Long id); // Borrará el registro
}
