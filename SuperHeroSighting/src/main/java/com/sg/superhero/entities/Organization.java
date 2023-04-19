package com.sg.superhero.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Organization {
    private int id;

    @NotBlank(message = "Name must not be empty.")
    @Size(max = 45, message = "Name must not be longer than 45 characters.")
    private String name;

    @Size(max = 100, message = "Description must not be longer than 100 characters.")
    private String description;

    @NotBlank(message = "Address must not be empty.")
    @Size(max = 100, message = "Address must not be longer than 100 characters.")
    private String address;

    @NotBlank(message = "Contact must not be empty.")
    @Size(max = 45, message = "Contact must not be longer than 45 characters.")
    private String contact;
    private List<SuperHero> members;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<SuperHero> getMembers() {
        return members;
    }

    public void setMembers(List<SuperHero> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(address, that.address) && Objects.equals(contact, that.contact);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
