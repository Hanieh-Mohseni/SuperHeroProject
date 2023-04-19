package com.sg.superhero.dao;

import com.sg.superhero.entities.Organization;
import com.sg.superhero.entities.SuperHero;
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
public class OrganizationDaoImpl implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM organization WHERE id = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrganizationMapper(), id);
            organization.setMembers(getMembersForOrganization(id));
            return organization;

        } catch(DataAccessException ex) {
            return null;
        }
    }

    private List<SuperHero> getMembersForOrganization(int id) {
        final String SELECT_HEROES_FOR_ORG = "SELECT sHero.* FROM superHero sHero "
                + "JOIN superOrganization superOrg ON sHero.id = superOrg.heroId "
                + "JOIN organization org ON superOrg.orgId = org.id WHERE org.id = ?";
        return jdbc.query(SELECT_HEROES_FOR_ORG, new SuperHeroDaoImpl.SuperHeroMapper(), id);
    }

    @Override
    public List<Organization> getAllOrganization() {
        final String SELECT_ALL_ORGANIZATION = "SELECT * FROM organization";
        return jdbc.query(SELECT_ALL_ORGANIZATION, new OrganizationMapper());
    }

    @Override
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORG = "INSERT INTO organization(name, description, address, contact) "
                + "VALUES(?, ?, ?, ?)";
        jdbc.update(INSERT_ORG, organization.getName(), organization.getDescription(), organization.getAddress(), organization.getContact());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);

        if(organization.getMembers() != null) {
            insertSuperOrganization(organization);
        }

        return organization;
    }

    private void insertSuperOrganization(Organization organization) {
        final String INSERT_SUPER_ORG = "INSERT INTO superOrganization(heroId, orgId) "
                + "VALUES(?, ?)";
        for(SuperHero member : organization.getMembers()) {
            jdbc.update(INSERT_SUPER_ORG, member.getId(), organization.getId());
        }
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORG = "UPDATE organization SET name = ?, description = ?, address = ?, contact = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_ORG,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getContact(),
                organization.getId());

        final String DELETE_SUPER_ORG = "DELETE FROM superOrganization WHERE orgId = ?";
        jdbc.update(DELETE_SUPER_ORG, organization.getId());

        if(organization.getMembers() != null) {
            insertSuperOrganization(organization);
        }
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_SUPER_ORGANIZATION = "DELETE FROM superOrganization WHERE orgId = ?";
        jdbc.update(DELETE_SUPER_ORGANIZATION, id);

        final String DELETE_ORG = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORG, id);
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            organization.setContact(rs.getString("contact"));
            return organization;
        }
    }
}
