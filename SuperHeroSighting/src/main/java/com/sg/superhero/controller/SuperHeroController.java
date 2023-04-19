package com.sg.superhero.controller;

import com.sg.superhero.dao.OrganizationDao;
import com.sg.superhero.dao.PowerDao;
import com.sg.superhero.dao.SuperHeroDao;
import com.sg.superhero.entities.Organization;
import com.sg.superhero.entities.Power;
import com.sg.superhero.entities.SuperHero;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SuperHeroController {
    @Autowired
    OrganizationDao organizationDao;
    @Autowired
    SuperHeroDao superHeroDao;
    @Autowired
    PowerDao powerDao;

    @GetMapping("superHeroes")
    public String displaySuperHeroes(Model model) {
        List<SuperHero> superHeroes = superHeroDao.getAllSuperHero();
        List<Organization> organizations = organizationDao.getAllOrganization();
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("superheroes", superHeroes);
        model.addAttribute("organizations", organizations);
        model.addAttribute("powers", powers);
        if (model.getAttribute("superhero") == null)
            model.addAttribute("superhero", new SuperHero());
        return "superHeroes";
    }

    @PostMapping("addHero")
    public String addSuperHero(@Valid @ModelAttribute("superhero") SuperHero superhero,
                               BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
        String powerId = request.getParameter("powerId");
        String[] orgIds = request.getParameterValues("orgId");

        if (powerId != null )
            superhero.setPower(powerDao.getPowerById(Integer.parseInt(powerId)));

        if (orgIds != null) {
            List<Organization> organizations = new ArrayList<>();
            for (String orgId : orgIds) {
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(orgId)));
            }
            superhero.setOrganization(organizations);
        }
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            List<SuperHero> superHeroes = superHeroDao.getAllSuperHero();
            List<Organization> organizations = organizationDao.getAllOrganization();
            List<Power> powers = powerDao.getAllPowers();
            model.addAttribute("superheroes", superHeroes);
            model.addAttribute("organizations", organizations);
            model.addAttribute("powers", powers);
            return "superHeroes";
        }

        superHeroDao.addSuperHero(superhero);

        return "redirect:/superHeroes";
    }

    @GetMapping("superHeroDetail")
    public String superHeroDetail(Integer id, Model model) {
        SuperHero hero = superHeroDao.getSuperHeroById(id);
        model.addAttribute("hero", hero);
        return "superHeroDetail";
    }

    @GetMapping("deleteSuperHero")
    public String deleteSuperHero(Integer id, Model model) {
        SuperHero hero = superHeroDao.getSuperHeroById(id);
        model.addAttribute("hero", hero);
        return "deleteSuperHero";
    }

    @GetMapping("/deleteSuperHero/{id}")
    public String deletePost(@PathVariable Integer id) {

        int status = superHeroDao.deleteSuperHeroById(id);

//        if (status == 0) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(id, HttpStatus.OK);
        return "redirect:/superHeroes";
    }

    @GetMapping("editSuperHero")
    public String editSuperHero(Integer id, Model model) {
        SuperHero hero = superHeroDao.getSuperHeroById(id);
        List<Power> powers = powerDao.getAllPowers();
        List<Organization> organizations = organizationDao.getAllOrganization();
        model.addAttribute("hero", hero);
        model.addAttribute("powers", powers);
        model.addAttribute("organizations", organizations);
        return "editSuperHero";
    }

    @PostMapping("editSuperHero")
    public String editSuperHero(@Valid @ModelAttribute("hero") SuperHero hero,
                                BindingResult bindingResult, ModelMap model, HttpServletRequest request) {
        String powerId = request.getParameter("powerId");
        String[] orgIds = request.getParameterValues("orgId");

        if (powerId != null)
            hero.setPower(powerDao.getPowerById(Integer.parseInt(powerId)));

        if (orgIds != null) {
            List<Organization> organizations = new ArrayList<>();
            for (String orgId : orgIds) {
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(orgId)));
            }
            hero.setOrganization(organizations);
        }
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            List<SuperHero> superHeroes = superHeroDao.getAllSuperHero();
            List<Organization> organizations = organizationDao.getAllOrganization();
            List<Power> powers = powerDao.getAllPowers();
            model.addAttribute("superheroes", superHeroes);
            model.addAttribute("organizations", organizations);
            model.addAttribute("powers", powers);
            return "editSuperHero";
        }
        superHeroDao.updateSuperHero(hero);

        return "redirect:/superHeroes";
    }
}
