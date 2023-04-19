package com.sg.superhero.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Location {
    private int id;
    private String name;
    private String description;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id && Objects.equals(name, location.name) && Objects.equals(description, location.description) && Objects.equals(address, location.address) && Objects.equals(latitude, location.latitude) && Objects.equals(longitude, location.longitude);
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longtitude=" + longitude +
                '}';
    }
}
