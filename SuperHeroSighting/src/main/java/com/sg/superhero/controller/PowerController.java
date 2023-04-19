package com.sg.superhero.controller;

import com.sg.superhero.dao.PowerDao;
import com.sg.superhero.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PowerController {
    @Autowired
    PowerDao powerDao;

    @GetMapping("powers")
    public String displayPowers(Model model) {
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        model.addAttribute("power", new Power());
        return "powers";
    }

    @PostMapping("addPower")
    public String addPower(@Valid @ModelAttribute Power power, BindingResult bindingResult, ModelMap model) {
        List<Power> powers = powerDao.getAllPowers();
        model.addAttribute("powers", powers);
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "powers";
        }
        powerDao.addPower(power);

        return "redirect:/powers";
    }

    @GetMapping("deletePower")
    public String deletePower(Integer id) {
        powerDao.deletePowerById(id);
        return "redirect:/powers";
    }

    @GetMapping("editPower")
    public String editPower(Integer id, Model model) {
        Power power = powerDao.getPowerById(id);
        model.addAttribute("power", power);
        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(@Valid @ModelAttribute Power power, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "editPower";
        }
        powerDao.updatePower(power);
        return "redirect:/powers";
    }

}
