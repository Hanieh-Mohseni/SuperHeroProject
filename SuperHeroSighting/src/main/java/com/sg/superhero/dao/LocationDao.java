package com.sg.superhero.dao;

import com.sg.superhero.entities.Location;

import java.util.List;

public interface LocationDao {
    /**
     * @param location Location object representing the Location to be added to the database
     * @return Created Location
     */
    Location createLocation(Location location) throws LocationException;
    
    /**
     * edits an existing location
     *
     * @param location Location object representing the edited location
     */
    void editLocation(Location location);
    
    /**
     * deletes a location by id
     *
     * @param locationId Integer representing the id of the location to delete
     */
    void deleteLocationById(int locationId) throws LocationException;
    
    /**
     *
     * @param locationId representing the id of the location to be retrieved
     * @return retrieved Location
     */
    Location getLocationById(int locationId) throws LocationException;
    
    /**
     * returns a list of all locations
     *
     * @return List<Location> of all the locations in the database
     */
    List<Location> getAllLocations();
}