package com.sg.superhero.dao;

import com.sg.superhero.entities.DisplaySighting;
import com.sg.superhero.entities.Location;
import com.sg.superhero.entities.Sighting;
import com.sg.superhero.entities.SuperHero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SightingDaoImpl implements SightingDao {
    @Autowired
    JdbcTemplate jdbc;
    
    public SightingDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    /**
     * @return List<Sighting> list of all sightings currently in the database
     */
    @Override
    public List<DisplaySighting> getAllSightings() throws SightingException {
        String query = "SELECT sh.name sh_name, l.name l_name, l.address l_address," +
                " l.id l_id, sh.id sh_id, s.occurDate, s.id FROM superhero sh" +
                " JOIN sighting s ON s.heroId = sh.id" +
                " JOIN location l ON s.locationId = l.id;";
        
        List<DisplaySighting> sightings;
        try {
            sightings = jdbc.query(query, new DisplaySightingRowMapper());
        } catch (Exception e) {
            throw new SightingException("Error Retrieving Sighting Information From Database");
        }
        
        sightings = sightings.stream()
                .sorted(Comparator.comparing(DisplaySighting::getOccurDate))
                .collect(Collectors.toList());
        Collections.reverse(sightings);
        
        return sightings;
    }
    
    /**
     * @param sighting Sighting Object to be added to the database
     * @return Sighting that was added to the database
     * @throws SightingException if the sighting cannot be added to the database,
     *                           if the sighting is invalid, or if the sighting already exists
     */
    @Override
    public Sighting createSighting(Sighting sighting) throws SightingException {
        DisplaySighting alreadyCreated = null;
        try {
            alreadyCreated = jdbc.queryForObject("SELECT sh.name sh_name, l.name l_name, l.address l_address," +
                            " l.id l_id, sh.id sh_id, s.occurDate, s.id FROM superhero sh" +
                            " JOIN sighting s ON s.heroId = sh.id" +
                            " JOIN location l ON s.locationId = l.id " +
                            "WHERE occurDate = ? AND heroId = ? AND locationId = ?;",
                    new DisplaySightingRowMapper(), sighting.getOccurDate(), sighting.getHeroId(), sighting.getLocationId());
        } catch (Exception ignored) {
        }
        
        if(alreadyCreated != null) {
            throw new SightingException("Sighting With Hero Name: '" + alreadyCreated.getHeroName()
                    + "'  Location Name: '" + alreadyCreated.getLocationName()
                    + "'  And Sighting Date: " + alreadyCreated.getOccurDate() + "'  Already Exists.");
        }
        
        
        String query = "INSERT INTO sighting (occurDate, heroId, locationId) VALUES (?, ?, ?)";
        try {
            jdbc.update(query, sighting.getOccurDate(), sighting.getHeroId(), sighting.getLocationId());
        } catch (Exception e) {
            throw new SightingException("Error Creating Sighting");
        }
        try {
            int sightingId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            sighting.setId(sightingId);
        } catch (Exception e) {
            throw new SightingException("Error Retrieving Id");
        }
        
        return sighting;
    }
    
    /**
     * @param id Integer representing the sighting to delete
     */
    @Override
    public void deleteSightingById(Integer id) {
        jdbc.update("DELETE FROM sighting WHERE id = ?", id);
    }
    
    /**
     * @param id Integer representing the id of the sighting to edit
     * @return edited Sighting Object
     */
    @Override
    public DisplaySighting getSightingById(Integer id) {
        String query = "SELECT sh.name sh_name, l.name l_name, l.address l_address," +
                " l.id l_id, sh.id sh_id, s.occurDate, s.id FROM superhero sh" +
                " JOIN sighting s ON s.heroId = sh.id" +
                " JOIN location l ON s.locationId = l.id WHERE s.id = ?;";
        
        return jdbc.queryForObject(query, new DisplaySightingRowMapper(), id);
    }
    
    /**
     * @param sighting Sighting Object that will replace a prior Sighting Object
     */
    @Override
    public void editSighting(Sighting sighting) throws SightingException {
        DisplaySighting alreadyCreated = null;
        try {
            alreadyCreated = jdbc.queryForObject("SELECT sh.name sh_name, l.name l_name, l.address l_address," +
                            " l.id l_id, sh.id sh_id, s.locationId locationId, s.heroId heroId, s.occurDate occurDate, s.id " +
                            "FROM sighting s JOIN superhero sh" +
                            " ON s.heroId = sh.id" +
                            " JOIN location l ON s.locationId = l.id " +
                            "WHERE occurDate = ? AND heroId = ? AND locationId = ? AND s.id != ?;",
                    new DisplaySightingRowMapper(), sighting.getOccurDate(),
                    sighting.getHeroId(), sighting.getLocationId(), sighting.getId());
        } catch (Exception ignored) {
        }
    
        if(alreadyCreated != null) {
            throw new SightingException("Sighting With Hero Name: '" + alreadyCreated.getHeroName()
                    + "'  Location Name: '" + alreadyCreated.getLocationName()
                    + "'  And Sighting Date: " + alreadyCreated.getOccurDate() + "'  Already Exists.");
        }
        
        final String UPDATE_SIGHTING = "UPDATE sighting SET occurDate = ?, heroId = ?,  locationId = ?"
                + " WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getOccurDate(),
                sighting.getHeroId(),
                sighting.getLocationId(),
                sighting.getId());
    }
    
    /**
     * report all the superheroes sighted at a particular location.
     *
     * @param locationId representing the id of the particular location
     * @return List<SuperHero> of all the superheros seen at a particular location
     * @throws SightingException if there are no heroes to be shown of the particular location
     *                           or if no location with that id exists
     */
    @Override
    public List<SuperHero> showAllHeroesForLocation(int locationId) throws SightingException {
        
        String query = "SELECT sh.* FROM superhero sh" +
                " JOIN sighting s ON s.heroId = sh.id" +
                " JOIN location l ON l.id = s.locationId WHERE s.locationId = ?";
        
        List<SuperHero> heroes;
        
        try {
            heroes = jdbc.query(query, new HeroRowMapper(), locationId);
        } catch (Exception e) {
            throw new SightingException("Could Not Retrieve Hero Information From Database");
        }
        return heroes;
    }
    
    /**
     * reports all the locations where a particular superhero has been seen.
     *
     * @param memberId int representing the superhero to find
     * @return List<Location> of all the locations a particular superHero has been sighted
     * @throws SightingException if there is no hero associated with the memberId
     *                           or if there are no Sightings for the hero
     */
    @Override
    public List<Location> showAllLocationsForHero(int memberId) throws SightingException {
        String query = "SELECT l.name, l.description, l.address, l.longitude, l.latitude FROM superhero sh" +
                " JOIN sighting s ON s.heroId = sh.id" +
                " JOIN location l ON l.id = s.locationId WHERE sh.id = ? AND s.heroId = ?";
        
        List<Location> locations;
        
        try {
            locations = jdbc.query(query, new LocationRowMapper(), memberId, memberId);
        } catch (Exception e) {
            throw new SightingException("Could Not Retrieve Location Information From Database");
        }
        
        return locations;
    }
    
    /**
     * @param date LocalDate  representing the dat to sort by
     * @return List<Sighting> of all the sightings for a particular date
     * @throws SightingException if the date provided is invalid or if there are no sightings for that date
     */
    @Override
    public List<DisplaySighting> showAllSightingsForDate(LocalDate date) throws SightingException {
        String query = "SELECT sh.name sh_name, l.name l_name, l.address l_address," +
                " l.id l_id, sh.id sh_id, s.occurDate, s.id FROM superhero sh" +
                " JOIN sighting s ON s.heroId = sh.id" +
                " JOIN location l ON s.locationId = l.id WHERE s.occurDate = ?";

        List<DisplaySighting> sightings;
        try {
            sightings = jdbc.query(query, new DisplaySightingRowMapper(), date);
        } catch (Exception e) {
            throw new SightingException("Error Retrieving Sighting Information From Database");
        }
        return sightings;
    }
    
    public static class LocationRowMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location l = new Location();
            
            l.setName(rs.getString("name"));
            l.setDescription(rs.getString("description"));
            l.setAddress(rs.getString("address"));
            l.setLongitude(rs.getBigDecimal("longitude"));
            l.setLatitude(rs.getBigDecimal("latitude"));
            
            return l;
        }
    }
    
    public static class HeroRowMapper implements RowMapper<SuperHero> {
        @Override
        public SuperHero mapRow(ResultSet rs, int rowNum) throws SQLException {
            SuperHero hero = new SuperHero();
            
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            
            return hero;
        }
    }
    
    public static class SightingRowMapper implements RowMapper<Sighting> {
        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            
            sighting.setId(rs.getInt("id"));
            sighting.setOccurDate(rs.getString("occurDate"));
            sighting.setLocationId(rs.getInt("locationId"));
            sighting.setHeroId(rs.getInt("heroId"));
            
            return sighting;
        }
    }
    
    public static class DisplaySightingRowMapper implements RowMapper<DisplaySighting> {
        @Override
        public DisplaySighting mapRow(ResultSet rs, int index) throws SQLException {
            
            DisplaySighting sighting = new DisplaySighting();
            sighting.setId(rs.getInt("id"));
            sighting.setOccurDate(rs.getString("occurDate"));
            sighting.setLocationName(rs.getString("l_name"));
            sighting.setLocationAddress(rs.getString("l_address"));
            sighting.setHeroName(rs.getString("sh_name"));
            sighting.setLocationId(rs.getInt("l_id"));
            sighting.setHeroId(rs.getInt("sh_id"));
            sighting.setHeroName(rs.getString("sh_name"));
            
            return sighting;
        }
    }
}
