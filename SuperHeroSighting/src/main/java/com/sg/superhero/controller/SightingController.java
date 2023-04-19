package com.sg.superhero.controller;

import com.sg.superhero.dao.LocationDao;
import com.sg.superhero.dao.LocationException;
import com.sg.superhero.dao.SightingDao;
import com.sg.superhero.dao.SightingException;
import com.sg.superhero.dao.SuperHeroDao;
import com.sg.superhero.entities.DisplaySighting;
import com.sg.superhero.entities.Location;
import com.sg.superhero.entities.Sighting;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class SightingController { //TODO: in .html gray out future dates or use popup bar
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    SuperHeroDao superHeroDao;
    
    @Autowired
    LocationDao locationDao;
    
    @PostMapping("createSighting")
    public String createSighting(@Valid @ModelAttribute("sighting") Sighting sighting, Model model,
                                 BindingResult bindingResult) throws SightingException {
        
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            model.addAttribute("members", superHeroDao.getAllSuperHero());
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("sightings", sightingDao.getAllSightings());
            return "sightings";
        }
        
        try {
            sightingDao.createSighting(sighting);
        }catch (SightingException e){
            model.addAttribute("members", superHeroDao.getAllSuperHero());
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("sightings", sightingDao.getAllSightings());
            model.addAttribute("displayError", e.getMessage());
            return "sightings";
        }
        return "redirect:/sightings";
    }
    
    @GetMapping("sightings")
    public String displaySightings(@NotNull Model model) throws SightingException {
        
        model.addAttribute("members", superHeroDao.getAllSuperHero());
        model.addAttribute("locations", locationDao.getAllLocations());
        model.addAttribute("sightings", sightingDao.getAllSightings());
        model.addAttribute("sighting", new Sighting());
        return "sightings";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        DisplaySighting dispSighting = sightingDao.getSightingById(id);
        
        Sighting sighting = new Sighting();
        sighting.setId(dispSighting.getId());
        sighting.setHeroId(dispSighting.getHeroId());
        sighting.setHeroName(dispSighting.getHeroName());
        sighting.setLocationId(dispSighting.getLocationId());
        sighting.setLocationName(dispSighting.getLocationName());
        sighting.setLocationAddress(dispSighting.getLocationAddress());
        sighting.setOccurDate(dispSighting.getOccurDate().toString());
        
        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locationDao.getAllLocations());
        model.addAttribute("members", superHeroDao.getAllSuperHero());
        model.addAttribute("displayError", "");
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(@Valid @ModelAttribute("locationId") int locationId,
                                      @Valid @ModelAttribute("sightingId") int sightingId,
                                      @Valid @ModelAttribute("heroId") int heroId,
                                      @Valid @ModelAttribute("occurDate") String occurDate,
                                      BindingResult bindingResult, Model model) throws SightingException {
    
        String locationName = null;
        String locationAddress = null;
        try {
            Location l = locationDao.getLocationById(locationId);
            locationName = l.getName();
            locationAddress = l.getAddress();
        } catch (LocationException ignored) {
        }
    
        String heroName = null;
        try {
            heroName = superHeroDao.getSuperHeroById(heroId).getName();
        } catch (Exception ignored) {
        }
    
        Sighting sighting = new Sighting();
        sighting.setId(sightingId);
        sighting.setHeroId(heroId);
        sighting.setHeroName(heroName);
        sighting.setLocationId(locationId);
        sighting.setLocationName(locationName);
        sighting.setLocationAddress(locationAddress);
        sighting.setOccurDate(occurDate);
        
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "editSighting";
        }
        try {
            sightingDao.editSighting(sighting);
        }catch (SightingException e){
            model.addAttribute("members", superHeroDao.getAllSuperHero());
            model.addAttribute("locations", locationDao.getAllLocations());
            model.addAttribute("sightings", sightingDao.getAllSightings());
            model.addAttribute("displayError", e.getMessage());
            DisplaySighting dispSighting = sightingDao.getSightingById(sightingId);

            Sighting s = new Sighting();
            s.setId(dispSighting.getId());
            s.setHeroId(dispSighting.getHeroId());
            s.setHeroName(dispSighting.getHeroName());
            s.setLocationId(dispSighting.getLocationId());
            s.setLocationName(dispSighting.getLocationName());
            s.setLocationAddress(dispSighting.getLocationAddress());
            s.setOccurDate(dispSighting.getOccurDate().toString());
            
            model.addAttribute("sighting", s);
            
            return "editSighting";
        }
        
        return "redirect:/sightings";
    }
    
    @GetMapping("searchByLocation")
    public String searchByLocation(Model model) {
        model.addAttribute("locations", locationDao.getAllLocations());
        return "searchByLocation";
    }
    
    @PostMapping("searchByLocation")
    public String searchByLocation(Model model, @Valid int locationId) throws SightingException, LocationException {
        model.addAttribute("heroes", sightingDao.showAllHeroesForLocation(locationId));
        model.addAttribute("locations", locationDao.getAllLocations());
        if(locationId != 0) {
            model.addAttribute("location", locationDao.getLocationById(locationId));
        }
        model.addAttribute("locationId", locationId);
        
        return "searchByLocation";
    }
    
    @GetMapping("searchByHero")
    public String searchByHero(Model model) {
        model.addAttribute("heroes", superHeroDao.getAllSuperHero());
        return "searchByHero";
    }
    
    @PostMapping("searchByHero")
    public String searchByHero(Model model, @Valid int heroId) throws SightingException {
        model.addAttribute("heroes", superHeroDao.getAllSuperHero());
        model.addAttribute("locations", sightingDao.showAllLocationsForHero(heroId));
        if(heroId != 0) {
            model.addAttribute("hero", superHeroDao.getSuperHeroById(heroId));
        }
        model.addAttribute("heroId", heroId);
        
        return "searchByHero";
    }
    
    @GetMapping("searchByDate")
    public String searchByDate() {
        return "searchByDate";
    }
    
    @PostMapping("searchByDate")
    public String searchByDate(Model model, String date) throws SightingException {
        model.addAttribute("sightings", sightingDao.showAllSightingsForDate(LocalDate.parse(date)));
        model.addAttribute("date", LocalDate.parse(date));
        return "searchByDate";
    }
}
