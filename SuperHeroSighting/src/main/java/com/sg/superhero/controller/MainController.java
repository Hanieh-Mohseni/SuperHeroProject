package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.entities.DisplaySighting;
import com.sg.superhero.entities.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperHeroDao superHeroDao;

    @Autowired
    LocationDao locationDao;
    
    
    @GetMapping("")
    public String displayMainPage(Model model) throws SightingException {
        model.addAttribute("message", "Welcome to the site Sighting of Super!");
        
        List<DisplaySighting> sightings = sightingDao.getAllSightings();
        if(sightings.size() > 10){
            sightings = sightings.subList(0,9);
        }
        
        model.addAttribute("sightings", sightings);
        return "home";
    }

    @GetMapping("map")
    public String displayMap(Model model) throws SightingException {
        model.addAttribute("latitude", "20.00");
        model.addAttribute("longitude", "-90.00");

        return "map";
    }

    @GetMapping("displayLocationForHero")
    public String displayHeroesForHero(Model model) {
        model.addAttribute("members", superHeroDao.getAllSuperHero());
        return "displayLocationForHero";
    }

    @PostMapping("displayLocationForHero")
    public String displayHeroesForHeroPost(Model model, int heroId) {
        try {
            model.addAttribute("heroes", sightingDao.showAllLocationsForHero(heroId));
        } catch (SightingException e) {

        }
        model.addAttribute("members", superHeroDao.getAllSuperHero());
        if(heroId != 0) {
            model.addAttribute("hero", superHeroDao.getSuperHeroById(heroId));
        }
        model.addAttribute("heroId", heroId);
        return "displayLocationForHero";
    }

    @GetMapping("displaySightingsForDate")
    public String displayHeroesForDate(Model model) {
        return "displaySightingsForDate";
    }

    @PostMapping("displaySightingsForDate")
    public String displaySightingsForDate(Model model, HttpServletRequest request) throws SightingException, LocationException {
        try {
            String occurDate = request.getParameter("occurDate");
            List<DisplaySighting> sightings = sightingDao.showAllSightingsForDate(LocalDate.parse(occurDate));
            model.addAttribute("occurDate", occurDate);

            model.addAttribute("sightings", sightings);
        }catch (SightingException e) {

        }
        return "displaySightingsForDate";
    }
}
