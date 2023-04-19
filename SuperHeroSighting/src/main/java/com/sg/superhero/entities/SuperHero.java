package com.sg.superhero.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class SuperHero {
    private int id;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 30, message = "Name must be less than 30 characters.")
    private String name;
    private String description;
    private Power power;
    private List<Organization> organization;

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

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public List<Organization> getOrganization() {
        return organization;
    }

    public void setOrganization(List<Organization> organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperHero superHero = (SuperHero) o;
        return id == superHero.id && Objects.equals(name, superHero.name) && Objects.equals(description, superHero.description) && Objects.equals(power, superHero.power) && Objects.equals(organization, superHero.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, power, organization);
    }

    @Override
    public String toString() {
        return "SuperHero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", power=" + power +
                ", organization=" + organization +
                '}';
    }
}
