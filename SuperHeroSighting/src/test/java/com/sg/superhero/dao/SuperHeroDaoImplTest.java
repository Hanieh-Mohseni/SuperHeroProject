package com.sg.superhero.dao;

import com.sg.superhero.entities.Organization;
import com.sg.superhero.entities.Power;
import com.sg.superhero.entities.SuperHero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class SuperHeroDaoImplTest {

    @Autowired
    private SuperHeroDao superHeroDao;
    @Autowired
    private PowerDao powerDao;
    @Autowired
    private OrganizationDao orgDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private List<SuperHero> allHeroes = new ArrayList<>();

    @Test
    void testGetSuperHeroById() {
        SuperHero hero = new SuperHero();
        hero.setName("heroName");
        hero.setDescription("description");
        Power power;
        List<Power> powers = powerDao.getAllPowers();
        if (powers != null && powers.size() > 0) {
            power = powers.get(0);
        } else {
            power = new Power();
            power.setName("newPower");
            power = powerDao.addPower(power);
        }
        hero.setPower(power);

        List<Organization> orgList = new ArrayList<>();
        Organization org;
        List<Organization> orgs = orgDao.getAllOrganization();
        if (orgs != null && orgs.size() > 0) {
            org = orgs.get(0);
            orgList.add(org);

        } else {
            org = new Organization();
            org.setName("newOrganization");
            org.setDescription("description for org");
            org.setAddress("address for org");
            org.setContact("contact number and name");
            org = orgDao.addOrganization(org);
            orgList.add(org);

            org = new Organization();
            org.setName("newOrganization1");
            org.setDescription("description for org1");
            org.setAddress("address for org1");
            org.setContact("contact number and name1");
            org = orgDao.addOrganization(org);
            orgList.add(org);
        }
        hero.setOrganization(orgList);

        hero = superHeroDao.addSuperHero(hero);
        assertNotNull(hero);

        SuperHero retrieveHero = superHeroDao.getSuperHeroById(hero.getId());
        assertNotNull(retrieveHero);
        assertEquals(hero, retrieveHero);
    }

    @Test
    void testAddAndGetAll() {
        SuperHero hero = new SuperHero();
        hero.setName("heroName");
        hero.setDescription("description");
        Power power;
        List<Power> powers = powerDao.getAllPowers();
        if (powers != null && powers.size() > 0) {
            power = powers.get(0);
        } else {
            power = new Power();
            power.setName("newPower");
            power = powerDao.addPower(power);
        }
        hero.setPower(power);

        List<Organization> orgList = new ArrayList<>();
        Organization org;
        List<Organization> orgs = orgDao.getAllOrganization();
        if (orgs != null && orgs.size() > 0) {
            org = orgs.get(0);
            orgList.add(org);

        } else {
            org = new Organization();
            org.setName("newOrganization");
            org.setDescription("description for org");
            org.setAddress("address for org");
            org.setContact("contact number and name");
            org = orgDao.addOrganization(org);
            orgList.add(org);

            org = new Organization();
            org.setName("newOrganization1");
            org.setDescription("description for org1");
            org.setAddress("address for org1");
            org.setContact("contact number and name1");
            org = orgDao.addOrganization(org);
            orgList.add(org);
        }
        hero.setOrganization(orgList);

        hero = superHeroDao.addSuperHero(hero);
        assertNotNull(hero);

        List<SuperHero> heroes = superHeroDao.getAllSuperHero();
        assertNotNull(heroes);
        hero.setOrganization(null);
        hero.setPower(null);
        assertTrue(heroes.contains(hero));
    }

    @Test
    void updateSuperHero() {
        SuperHero hero = new SuperHero();
        hero.setName("heroName");
        hero.setDescription("description");
        Power power;
        List<Power> powers = powerDao.getAllPowers();
        if (powers != null && powers.size() > 0) {
            power = powers.get(0);
        } else {
            power = new Power();
            power.setName("newPower");
            power = powerDao.addPower(power);
        }
        hero.setPower(power);

        List<Organization> orgList = new ArrayList<>();
        Organization org;
        List<Organization> orgs = orgDao.getAllOrganization();
        if (orgs != null && orgs.size() > 0) {
            org = orgs.get(0);
            orgList.add(org);

        } else {
            org = new Organization();
            org.setName("newOrganization");
            org.setDescription("description for org");
            org.setAddress("address for org");
            org.setContact("contact number and name");
            org = orgDao.addOrganization(org);
            orgList.add(org);

            org = new Organization();
            org.setName("newOrganization1");
            org.setDescription("description for org1");
            org.setAddress("address for org1");
            org.setContact("contact number and name1");
            org = orgDao.addOrganization(org);
            orgList.add(org);
        }
        hero.setOrganization(orgList);

        hero = superHeroDao.addSuperHero(hero);
        SuperHero retrieveHero = superHeroDao.getSuperHeroById(hero.getId());
        assertNotNull(retrieveHero);
        assertEquals(hero, retrieveHero);
        hero.setName("changeName");
        superHeroDao.updateSuperHero(hero);
        retrieveHero = superHeroDao.getSuperHeroById(hero.getId());
        assertEquals(hero, retrieveHero);
    }

    @Test
    void deleteSuperHeroById() {
        SuperHero hero = new SuperHero();
        hero.setName("heroName");
        hero.setDescription("description");
        Power power;
        List<Power> powers = powerDao.getAllPowers();
        if (powers != null && powers.size() > 0) {
            power = powers.get(0);
        } else {
            power = new Power();
            power.setName("newPower");
            power = powerDao.addPower(power);
        }
        hero.setPower(power);

        List<Organization> orgList = new ArrayList<>();
        Organization org;
        List<Organization> orgs = orgDao.getAllOrganization();
        if (orgs != null && orgs.size() > 0) {
            org = orgs.get(0);
            orgList.add(org);

        } else {
            org = new Organization();
            org.setName("newOrganization");
            org.setDescription("description for org");
            org.setAddress("address for org");
            org.setContact("contact number and name");
            org = orgDao.addOrganization(org);
            orgList.add(org);

            org = new Organization();
            org.setName("newOrganization1");
            org.setDescription("description for org1");
            org.setAddress("address for org1");
            org.setContact("contact number and name1");
            org = orgDao.addOrganization(org);
            orgList.add(org);
        }
        hero.setOrganization(orgList);

        hero = superHeroDao.addSuperHero(hero);
        SuperHero retrieveHero = superHeroDao.getSuperHeroById(hero.getId());
        assertNotNull(retrieveHero);
        assertEquals(hero, retrieveHero);

        superHeroDao.deleteSuperHeroById(hero.getId());

        final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM superhero WHERE id = ?";
        final int id = hero.getId();
        assertThrows(DataAccessException.class, () ->
                jdbcTemplate.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperHeroDaoImpl.SuperHeroMapper(), id));

    }

}