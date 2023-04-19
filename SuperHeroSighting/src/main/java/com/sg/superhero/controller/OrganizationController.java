package com.sg.superhero.controller;

import com.sg.superhero.dao.OrganizationDao;
import com.sg.superhero.dao.SuperHeroDao;
import com.sg.superhero.entities.Organization;
import com.sg.superhero.entities.SuperHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class OrganizationController {

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private SuperHeroDao superHeroDao;

    Set<ConstraintViolation<Organization>> addViolations = new HashSet<>();
    Set<ConstraintViolation<Organization>> editViolations = new HashSet<>();

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> organizationList = organizationDao.getAllOrganization();
        List<SuperHero> superHeroList = superHeroDao.getAllSuperHero();

        model.addAttribute("organizations", organizationList);
        model.addAttribute("superheroes", superHeroList);
        model.addAttribute("errors", addViolations);
        addViolations.clear();
        editViolations.clear();

        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, Model model, HttpServletRequest request) {
        String[] memberIds = request.getParameterValues("heroId");

        if(memberIds != null) {
            List<SuperHero> superHeroList = new ArrayList<>();

            for(String memberId : memberIds) {
                superHeroList.add(superHeroDao.getSuperHeroById(Integer.parseInt(memberId)));
            }
            organization.setMembers(superHeroList);
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        addViolations = validate.validate(organization);

        if(addViolations.isEmpty()) {
            organizationDao.addOrganization(organization);
            model.addAttribute("errors", addViolations);
            addViolations.clear();
            return "redirect:/organizations";
        } else {
            List<Organization> organizationList = organizationDao.getAllOrganization();
            List<SuperHero> superHeroList = superHeroDao.getAllSuperHero();

            model.addAttribute("organizations", organizationList);
            model.addAttribute("superheroes", superHeroList);
            model.addAttribute("organization", organization);
            model.addAttribute("errors", addViolations);
        }

        return "organizations";
    }

    @GetMapping("organizationsDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationsDetail";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<SuperHero> superHeroList = superHeroDao.getAllSuperHero();

        model.addAttribute("organization", organization);
        model.addAttribute("superheroes", superHeroList);
        model.addAttribute("errors", editViolations);

        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(Organization organization, HttpServletRequest request) {
        String[] memberIds = request.getParameterValues("heroId");

        if(memberIds != null) {
            List<SuperHero> superHeroList = new ArrayList<>();

            for(String memberId : memberIds) {
                superHeroList.add(superHeroDao.getSuperHeroById(Integer.parseInt(memberId)));
            }
            organization.setMembers(superHeroList);
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        editViolations = validate.validate(organization);

        if(editViolations.isEmpty()) {
            organizationDao.updateOrganization(organization);
            return "redirect:/organizations";
        }

        return "redirect:/editOrganization?id=" + organization.getId();
    }
}
