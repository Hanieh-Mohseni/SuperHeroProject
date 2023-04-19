package com.sg.superhero.controller;

import com.sg.superhero.dao.LocationDao;
import com.sg.superhero.dao.LocationException;
import com.sg.superhero.dao.SightingDao;
import com.sg.superhero.dao.SightingException;
import com.sg.superhero.dao.SuperHeroDao;
import com.sg.superhero.entities.Location;
import com.sg.superhero.entities.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
public class LocationController {
    @Autowired
    LocationDao locationDao;
    
    
    @PostMapping("createLocation")
    public String createLocation(String name, String description, String address, String latitude, String longitude) throws LocationException {
        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLatitude(new BigDecimal(latitude));
        location.setLongitude(new BigDecimal(longitude));
        locationDao.createLocation(location);
        
        return "redirect:/locations";
    }
    
    @GetMapping("locations")
    public String displaySightings(Model model) {
        model.addAttribute("locations", locationDao.getAllLocations());
        return "locations";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) throws LocationException {
        locationDao.deleteLocationById(id);
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) throws LocationException {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String performEditLocation(@Valid @ModelAttribute Location location, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "editLocation";
        }
        locationDao.editLocation(location);
        return "redirect:/locations";
    }
}
