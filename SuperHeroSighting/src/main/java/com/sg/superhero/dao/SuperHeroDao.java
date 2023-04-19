package com.sg.superhero.dao;

import com.sg.superhero.entities.SuperHero;

import java.util.List;

public interface SuperHeroDao {
    SuperHero getSuperHeroById(int id);
    List<SuperHero> getAllSuperHero();
    SuperHero addSuperHero(SuperHero hero);
    void updateSuperHero(SuperHero hero);
    int deleteSuperHeroById(int id);

}
