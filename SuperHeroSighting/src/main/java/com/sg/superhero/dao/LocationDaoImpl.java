package com.sg.superhero.dao;

import com.sg.superhero.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class LocationDaoImpl implements LocationDao {
    @Autowired
    JdbcTemplate jdbc;
    
    public LocationDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    /**
     * @param location Location object representing the Location to be added to the database
     * @return Created Location
     */
    @Override
    public Location createLocation(Location location) throws LocationException {
        Location checkExists = null;
        try {
            checkExists = jdbc.queryForObject(
                    "SELECT * FROM location WHERE name = ? AND address = ? AND latitude = ? AND Longitude = ?;",
                    new LocationRowMapper());
        } catch (Exception ignored) {
        }
        
        if(checkExists != null) {
            throw new LocationException("The Location You Are Trying To Create Already Exists");
        }
        
        final String query = "INSERT INTO location (name, description, address, latitude, longitude) " +
                "VALUES (?, ?, ?, ?, ?);";
        
        if(location == null) {
            throw new LocationException("Location Cannot Be Null");
        }
        
        if(location.getName() == null || location.getName().isBlank()) {
            throw new LocationException("The Location Name Is Not Valid");
        }
        
        if(location.getAddress() == null || location.getAddress().isBlank()) {
            throw new LocationException("The Location Address Is Not Valid");
        }
        try {
            jdbc.update(query, location.getName(),
                    location.getDescription(), location.getAddress(), location.getLatitude(), location.getLongitude());
        } catch (Exception e) {
            throw new LocationException("Error Creating Sighting");
        }
        
        int locationId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        
        location.setId(locationId);
        
        return location;
    }
    
    /**
     * edits an existing location
     *
     * @param location Location object representing the edited location
     */
    @Override
    public void editLocation(Location location) {
        String query = "UPDATE location SET name = ?, description = ?, address = ?, longitude = ?," +
                " latitude = ? WHERE id = ?;";
        jdbc.update(query,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLongitude(),
                location.getLatitude(),
                location.getId());
    }
    
    /**
     * deletes a location by id
     *
     * @param locationId Integer representing the id of the location to delete
     */
    @Override
    public void deleteLocationById(int locationId) throws LocationException {
        //if location is in use throw error
        Location checkUsed = null;
        try {
            checkUsed = jdbc.queryForObject(
                    "SELECT l.* FROM location l JOIN sighting s ON s.locationId = l.id WHERE l.id = ?;",
                    new LocationRowMapper(), locationId);
        } catch (Exception ignored) {
        
        }
        
        if(checkUsed != null) {
            throw new LocationException("Can Not Delete A Location Used In A Sighting");
        }
        
        jdbc.update("DELETE FROM location WHERE id = ?;", locationId);
    }
    
    /**
     * @param locationId representing the id of the location to be retrieved
     * @return retrieved Location
     */
    @Override
    public Location getLocationById(int locationId) throws LocationException {
        Location location = null;
        try {
            location = jdbc.queryForObject("SELECT * FROM location WHERE id = ?;", new LocationRowMapper(), locationId);
        } catch (Exception ignored) {
        }
        
        if(location == null) {
            throw new LocationException("The Location With Id: " + locationId + " Does Not Exist");
        }
        
        return location;
    }
    
    /**
     * returns a list of all locations
     *
     * @return List<Location> of all the locations in the database
     */
    @Override
    public List<Location> getAllLocations() {
        List<Location> locations = jdbc.query("SELECT * FROM location;", new LocationRowMapper());
        locations.forEach((location -> {
            BigDecimal latitude = location.getLatitude();
            BigDecimal longitude = location.getLongitude();
            
            latitude = latitude.setScale(2, RoundingMode.HALF_EVEN);
            longitude = longitude.setScale(2, RoundingMode.HALF_EVEN);
            
            location.setLongitude(longitude);
            location.setLatitude(latitude);
        }));
        
        return locations;
    }
    
    public static class LocationRowMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
            Location l = new Location();
            
            l.setId(rs.getInt("id"));
            l.setName(rs.getString("name"));
            l.setDescription(rs.getString("description"));
            l.setAddress(rs.getString("address"));
            l.setLongitude(rs.getBigDecimal("longitude"));
            l.setLatitude(rs.getBigDecimal("latitude"));
            
            return l;
        }
    }
}