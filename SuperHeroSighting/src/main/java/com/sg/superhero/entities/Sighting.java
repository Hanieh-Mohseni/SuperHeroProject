package com.sg.superhero.entities;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;

public class Sighting {
    private int id;
    private String occurDate;
    private String picture;
    private String heroName;
    @Min(message = "Must Select A Hero", value = 1)
    private int heroId;
    private String locationName;
    @Min(message = "Must Select A Location", value = 1)
    private int locationId;
    private String locationAddress;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getOccurDate() {
        return occurDate;
    }
    
    public void setOccurDate(String occurDate) {
        this.occurDate = occurDate;
    }
    public String getPicture() {
        return picture;
    }
    
    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    public int getHeroId() {
        return heroId;
    }
    
    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }
    
    public int getLocationId() {
        return locationId;
    }
    
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    
    public String getHeroName() {
        return heroName;
    }
    
    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public String getLocationAddress() {
        return locationAddress;
    }
    
    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return id == sighting.id && heroId == sighting.heroId && locationId == sighting.locationId && Objects.equals(occurDate, sighting.occurDate) && Objects.equals(picture, sighting.picture);
    }
    
    @Override
    public String toString() {
        return "Sight{" +
                "id=" + id +
                ", occurDate=" + occurDate +
                ", picture='" + picture + '\'' +
                ", memberId=" + heroId +
                ", locationId=" + locationId +
                '}';
    }
}
