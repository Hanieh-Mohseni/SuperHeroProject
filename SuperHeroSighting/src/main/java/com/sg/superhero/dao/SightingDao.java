package com.sg.superhero.dao;

import com.sg.superhero.entities.DisplaySighting;
import com.sg.superhero.entities.Location;
import com.sg.superhero.entities.Sighting;
import com.sg.superhero.entities.SuperHero;

import java.time.LocalDate;
import java.util.List;

public interface SightingDao {
    /**
     * @return List<Sighting> list of all sightings currently in the database
     */
    List<DisplaySighting> getAllSightings() throws SightingException;
    
    /**
     * @param sighting Sighting Object to be added to the database
     * @return Sighting that was added to the database
     * @throws SightingException if the sighting cannot be added to the database,
     *                           if the sighting is invalid, or if the sighting already exists
     */
    Sighting createSighting(Sighting sighting) throws SightingException;
    
    /**
     * @param id Integer representing the sighting to delete
     */
    void deleteSightingById(Integer id);
    
    /**
     * @param id Integer representing the id of the sighting to edit
     * @return edited Sighting Object
     */
    DisplaySighting getSightingById(Integer id);
    
    /**
     * @param sighting Sighting Object that will replace a prior Sighting Object
     */
    void editSighting(Sighting sighting) throws SightingException;
    
    /**
     * @param locationId representing the id of the particular location
     * @return List<SuperHero> of all the superheros seen at a particular location
     * @throws SightingException if there are no heroes to be shown of the particular location
     *                           or if no location with that id exists
     */
    List<SuperHero> showAllHeroesForLocation(int locationId) throws SightingException;
    
    /**
     * @param memberId int representing the superhero to find
     * @return List<Location> of all the locations a particular superHero has been sighted
     * @throws SightingException if there is no hero associated with the memberId
     *                           or if there are no Sightings for the hero
     */
    List<Location> showAllLocationsForHero(int memberId) throws SightingException;
    
    /**
     * @param date LocalDate  representing the dat to sort by
     * @return List<Sighting> of all the sightings for a particular date
     * @throws SightingException if the date provided is invalid or if there are no sightings for that date
     */
    List<DisplaySighting> showAllSightingsForDate(LocalDate date) throws SightingException;
}