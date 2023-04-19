package com.sg.superhero.dao;

import com.sg.superhero.entities.Organization;
import com.sg.superhero.entities.Power;
import com.sg.superhero.entities.SuperHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SuperHeroDaoImpl implements SuperHeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SuperHero getSuperHeroById(int id) {
        try {
            final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
            SuperHero hero = jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperHeroMapper(), id);
            hero.setPower(getPowerForHero(id));
            hero.setOrganization(getOrganizationForHero(id));
            return hero;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private Power getPowerForHero(int id) {
        final String SELECT_POWER_FOR_HERO = "SELECT p.* FROM power p "
                + "JOIN SUPERPOWER s " +
                "ON s.powerId = p.id " +
                "JOIN superhero h  " +
                "ON s.heroId = h.id WHERE h.id = ?";
        return DataAccessUtils.singleResult(jdbc.query(SELECT_POWER_FOR_HERO, new PowerDaoImpl.PowerMapper(), id));
    }

    private List<Organization> getOrganizationForHero(int id) {
        final String SELECT_ORGANIZATION_FOR_HERO = "SELECT o.* FROM organization o "
                + "JOIN superOrganization supOrg ON o.id = supOrg.orgId "
                + "JOIN superhero h ON supOrg.heroId = h.id WHERE h.id = ?";
        return jdbc.query(SELECT_ORGANIZATION_FOR_HERO,
                new OrganizationDaoImpl.OrganizationMapper(), id);
    }

    @Override
    public List<SuperHero> getAllSuperHero() {
        final String SELECT_ALL_SUPERHERO = "SELECT * FROM superhero";
        return jdbc.query(SELECT_ALL_SUPERHERO, new SuperHeroMapper());
    }

    @Override
    @Transactional
    public SuperHero addSuperHero(SuperHero hero) {
        final String INSERT_SUPERHERO = "INSERT INTO superhero(name, description) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_SUPERHERO,
                hero.getName(),
                hero.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);

        if (hero.getPower() != null)
            jdbc.update("INSERT INTO SuperPower(heroId, powerId) VALUES(?, ?);",
                    hero.getId(), hero.getPower().getId());
        if (hero.getOrganization() != null) insertSuperOrganization(hero);
        return hero;
    }

    private void insertSuperOrganization(SuperHero hero) {
        final String INSERT_HERO_ORGANIZATION = "INSERT INTO "
                + "superOrganization(heroId, orgId) VALUES(?,?)";
        for(Organization organization : hero.getOrganization()) {
            jdbc.update(INSERT_HERO_ORGANIZATION,
                    hero.getId(),
                    organization.getId());
        }
    }

    @Override
    public void updateSuperHero(SuperHero superHero) {
        final String UPDATE_SUPERHERO = "UPDATE superhero SET name = ?, description = ? "
                + " WHERE id = ?";
        jdbc.update(UPDATE_SUPERHERO,
                superHero.getName(),
                superHero.getDescription(),
                superHero.getId());

        final String DELETE_SUPERHERO_POWER = "DELETE FROM SuperPower WHERE heroId = ?";
        jdbc.update(DELETE_SUPERHERO_POWER, superHero.getId());
        if (superHero.getPower() != null) {
            final String INSERT_SUPERHERO_POWER = "INSERT INTO SuperPower(heroId, powerId) VALUES (?, ?)";
            jdbc.update(INSERT_SUPERHERO_POWER, superHero.getId(), superHero.getPower().getId());
        }
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superOrganization  WHERE heroId = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, superHero.getId());
        if (superHero.getOrganization() != null) insertSuperOrganization(superHero);
    }

    @Override
    @Transactional
    public int deleteSuperHeroById(int id) {
        final String DELETE_SUPERHERO_ORGANIZATION = "DELETE FROM superOrganization  WHERE heroId = ?";
        jdbc.update(DELETE_SUPERHERO_ORGANIZATION, id);
        final String DELETE_SUPERHERO_POWER = "DELETE FROM superPower  WHERE heroId = ?";
        jdbc.update(DELETE_SUPERHERO_POWER, id);
        final String DELETE_SUPERHERO = "DELETE FROM superhero  WHERE id = ?";
        int status = jdbc.update(DELETE_SUPERHERO, id);
        return status;
    }

    public static final class SuperHeroMapper implements RowMapper<SuperHero> {
        @Override
        public SuperHero mapRow(ResultSet rs, int index) throws SQLException {
            SuperHero hero = new SuperHero();
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            return hero;
        }
    }
}
