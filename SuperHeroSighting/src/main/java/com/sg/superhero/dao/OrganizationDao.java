package com.sg.superhero.dao;

import com.sg.superhero.entities.Organization;
import com.sg.superhero.entities.SuperHero;

import java.util.List;

public interface OrganizationDao {
    Organization getOrganizationById(int id);
    List<Organization> getAllOrganization();
    Organization addOrganization(Organization course);
    void updateOrganization(Organization course);
    void deleteOrganizationById(int id);
}
