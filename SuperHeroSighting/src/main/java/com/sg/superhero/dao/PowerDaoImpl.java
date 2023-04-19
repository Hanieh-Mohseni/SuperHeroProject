package com.sg.superhero.dao;

import com.sg.superhero.entities.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PowerDaoImpl implements PowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Power addPower(Power power) {
        final String INSERT_POWER = "INSERT INTO power(name) "
                + "VALUES(?)";
        jdbc.update(INSERT_POWER,
                power.getName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        power.setId(newId);
        return power;
    }

    @Override
    public Power getPowerById(int id) {
        try {
            final String SELECT_POWER_BY_ID = "SELECT * FROM power WHERE id = ?";
            return jdbc.queryForObject(SELECT_POWER_BY_ID, new PowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Power> getAllPowers() {
        final String SELECT_ALL_POWERS = "SELECT * FROM power";
        return jdbc.query(SELECT_ALL_POWERS, new PowerMapper());
    }

    @Override
    public void editPower(Power power) {
        final String UPDATE_POWER = "UPDATE power SET name = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_POWER,
                power.getName(),
                power.getId());
    }

    @Override
    public void updatePower(Power power) {
        final String UPDATE_POWER = "UPDATE power SET name = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_POWER,
                power.getName(),
                power.getId());
    }

    @Override
    @Transactional
    public void deletePowerById(int id) {
        final String DELETE_HERO_POWER = "DELETE FROM SuperPower WHERE powerId = ?";
        jdbc.update(DELETE_HERO_POWER, id);

        final String DELETE_POWER = "DELETE FROM power WHERE id = ?";
        jdbc.update(DELETE_POWER, id);
    }

    public static final class PowerMapper implements RowMapper<Power> {

        @Override
        public Power mapRow(ResultSet rs, int index) throws SQLException {
            Power power = new Power();
            power.setId(rs.getInt("id"));
            power.setName(rs.getString("name"));

            return power;
        }
    }
}
