package com.sg.superhero.dao;

import com.sg.superhero.entities.Power;

import java.util.List;

public interface PowerDao {
    Power addPower(Power power);
    Power getPowerById(int id);
    List<Power> getAllPowers();
    void editPower(Power power);
    void updatePower(Power power);
    void deletePowerById(int id);

}
