package com.portfolio.my_portfolio_backend.repository;

import com.portfolio.my_portfolio_backend.model.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SkillRepositoryImpl implements ISkillRepository {

    private final JdbcTemplate jdbcTemplate;

    // El "Traductor" de SQL a Objeto Skill
    private final RowMapper<Skill> skillRowMapper = (rs, num) -> {
        Skill skill = new Skill();
        skill.setId(rs.getLong("id"));
        skill.setName(rs.getString("name"));
        skill.setLevelPercentage(rs.getInt("level_percentage"));
        skill.setIconClass(rs.getString("icon_class"));
        skill.setPersonalInfoId(rs.getLong("personal_info_id"));
        return skill;
    };

    @Override
    public Skill save(Skill skill) {
        if (skill.getId() == null) {
            // LÓGICA INSERT (CREAR)
            //
            String sql = "INSERT INTO skills (name, level_percentage, icon_class, personal_info_id) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                ps.setString(1, skill.getName());
                ps.setInt(2, skill.getLevelPercentage());
                ps.setString(3, skill.getIconClass());
                ps.setLong(4, skill.getPersonalInfoId());
                return ps;
            }, keyHolder);

            skill.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        } else {
            // LÓGICA UPDATE (ACTUALIZAR)
            String sql = "UPDATE skills SET name = ?, level_percentage = ?, icon_class = ?, personal_info_id = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    skill.getName(),
                    skill.getLevelPercentage(),
                    skill.getIconClass(),
                    skill.getPersonalInfoId(),
                    skill.getId()
            );
        }
        return skill;
    }

    @Override
    public Optional<Skill> findById(Long id) {
        String sql = "SELECT * FROM skills WHERE id = ?";
        try {
            // Buscamos un único objeto
            Skill skill = jdbcTemplate.queryForObject(sql, skillRowMapper, id);
            return Optional.ofNullable(skill);
        } catch (EmptyResultDataAccessException e) {
            // Si no hay nada, devolvemos la "caja vacía"
            return Optional.empty();
        }
    }

    @Override
    public List<Skill> findAll() {
        String sql = "SELECT id, name, level_percentage, icon_class, personal_info_id FROM skills";
        return jdbcTemplate.query(sql, skillRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        // Corregido: Añadimos la columna "id" después del WHERE
        String sql = "DELETE FROM skills WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Skill> findByPersonalInfoId(Long personalInfoId) {
        // Añadimos el WHERE para filtrar por el ID de la persona
        String sql = "SELECT id, name, level_percentage, icon_class, personal_info_id " +
                "FROM skills WHERE personal_info_id = ?";

        return jdbcTemplate.query(sql, skillRowMapper, personalInfoId);
    }
}