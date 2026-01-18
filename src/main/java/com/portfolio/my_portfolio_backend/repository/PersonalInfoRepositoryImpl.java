package com.portfolio.my_portfolio_backend.repository;

import com.portfolio.my_portfolio_backend.model.PersonalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository // Esta clase es la encargada de entrar y salir de la base de datos de Neon".
@RequiredArgsConstructor
public class PersonalInfoRepositoryImpl implements IPersonalInfoRepository{

    private final JdbcTemplate jdbcTemplate;

    // Este es el "Traductor" oficial: pasa datos de la TABLA al OBJETO JAVA
    private final RowMapper<PersonalInfo> personalInfoRowMapper = (rs, rowNum) -> {
        PersonalInfo info = new PersonalInfo();

        // Mapeamos cada columna del schema.sql al atributo de la clase Java
        info.setId(rs.getLong("id"));
        info.setFirstName(rs.getString("first_name"));
        info.setLastName(rs.getString("last_name"));
        info.setTitle(rs.getString("title"));
        info.setProfileDescription(rs.getString("profile_description"));
        info.setProfileImageUrl(rs.getString("profile_image_url"));

        // Uso de getObject para manejar nulos correctamente
        info.setYearsOfExperience(rs.getObject("years_of_experience", Integer.class));

        info.setEmail(rs.getString("email"));
        info.setPhone(rs.getString("phone"));
        info.setLinkedinUrl(rs.getString("linkedin_url"));
        info.setGithubUrl(rs.getString("github_url"));

        return info;
    };



    private final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer;

    @Override
    public PersonalInfo save(PersonalInfo personalInfo) {
        //LOGICA PARA CREAR (INSERT) --> EN EL CASO DE QUE EL ID SEA NULL, ES DECIR, NO EXISTA
        if(personalInfo.getId()==null){
            String sql = "INSERT INTO personal_info (first_name, last_name, title, profile_description, profile_image_url, years_of_experience, email, phone, linkedin_url, github_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                // 1. Preparamos la sentencia y avisamos que queremos el "id" de vuelta
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"}); // le decimos que cre un array de Striings con la ID

                // 2. Rellenamos los huecos (?) uno por uno
                ps.setString(1, personalInfo.getFirstName());
                ps.setString(2, personalInfo.getLastName());
                ps.setString(3, personalInfo.getTitle());
                ps.setString(4, personalInfo.getProfileDescription());
                ps.setString(5, personalInfo.getProfileImageUrl());

                // 3. Manejo de nulos para números (Years of Experience)
                if (personalInfo.getYearsOfExperience() != null) {
                    ps.setInt(6, personalInfo.getYearsOfExperience());
                } else {
                    ps.setNull(6, java.sql.Types.INTEGER);
                }

                ps.setString(7, personalInfo.getEmail());
                ps.setString(8, personalInfo.getPhone());
                ps.setString(9, personalInfo.getLinkedinUrl());
                ps.setString(10, personalInfo.getGithubUrl());

                return ps; // Entregamos el "paquete" preparado a Spring
                        }, keyHolder); // El keyHolder se queda fuera esperando el ID


            personalInfo.setId(Objects.requireNonNull(keyHolder.getKey().longValue()));

        }else {
            //LOGICA PARA ACTUALZIAR (UPDATE)
            String sql = "UPDATE personal_info SET first_name = ?, last_name = ?, title = ?, profile_description = ?, profile_image_url = ?, years_of_experience = ?, email = ?, phone = ?, linkedin_url = ?, github_url = ? WHERE id = ?";

            jdbcTemplate.update(sql,
                    personalInfo.getFirstName(),
                    personalInfo.getLastName(),
                    personalInfo.getTitle(),
                    personalInfo.getProfileDescription(),
                    personalInfo.getProfileImageUrl(),
                    personalInfo.getYearsOfExperience(),
                    personalInfo.getEmail(),
                    personalInfo.getPhone(),
                    personalInfo.getLinkedinUrl(),
                    personalInfo.getGithubUrl(),
                    personalInfo.getId()

            );
        }

        return personalInfo;
    }

//    @Override
//    public Optional<PersonalInfo> findbyId(Long id) {
//
//        String sql = "Select * From personal_info Where id = ?";
//        List<PersonalInfo> infos = jdbcTemplate.query(sql, personalInfoRowMapper);
//        return infos.stream().findFirst();
//    }

    @Override
    //tipo de acceso + caja que puede contener personalInfo + nombre del metodo (que tipo de dato tiene que buscar + lo que tiene que buscar)
    public Optional<PersonalInfo> findbyId(Long id) {

        String sql = "Select * From personal_info Where id = ?";
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, personalInfoRowMapper, id));

        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    @Override
    public List<PersonalInfo> findAll() {
        String sql = "SELECT * FROM personal_info";
        return jdbcTemplate.query(sql, personalInfoRowMapper);
        //1º PARAMETRO --> Es un String que contiene la sentencia sql (ej, select * from)
        //2º PARAMETRO --> transfora el dato de la fila de la tabla  en un objeto java
    }

    @Override
    public void deleteById(Long id) {
        String sql = "Delete From personal_info Where id = ?"; //Deleteamos el id que nos llega por el parametro -> "?"
        jdbcTemplate.update(sql, id); // usamos update porque vamos a modificar (borrar) la bbdd, no ha pedir datos
    }
}
