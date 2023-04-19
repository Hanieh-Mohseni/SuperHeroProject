package com.sg.superhero.entities;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DisplaySighting {
    private int id;
    @Past(message = "Future Sightings Cannot Be Reported")
    private LocalDate occurDate;
    private String picture;
    @NotBlank(message = "Must Select A Hero")
    private String heroName;
    @Min(message = "Must Select A Hero", value = 1)
    private int heroId;
    @NotBlank(message = "Must Select A Location")
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
    
    public LocalDate getOccurDate() {
        return this.occurDate;
    }
    
    public void setOccurDate(String formattedDate) {
        this.occurDate = LocalDate.parse(formattedDate);
    }
    
    public String getPicture() {
        return picture;
    }
    
    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    public String getHeroName() {
        return heroName;
    }
    
    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
    
    public int getHeroId() {
        return heroId;
    }
    
    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public int getLocationId() {
        return locationId;
    }
    
    public void setLocationId(int locationId) {
        this.locationId = locationId;
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
        DisplaySighting that = (DisplaySighting) o;
        return id == that.id && occurDate.equals(that.occurDate) && picture.equals(that.picture) && heroName.equals(that.heroName) && locationName.equals(that.locationName);
    }
    
    @Override
    public String toString() {
        return "Sight{" +
                "id=" + id +
                ", occurDate=" + occurDate +
                ", picture='" + picture + '\'' +
                ", memberName=" + heroName +
                ", locationName=" + locationName +
                '}';
    }
}