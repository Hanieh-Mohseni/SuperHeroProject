package com.sg.superhero.entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Power {
    private int id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 15, message="Name must be 3 to 15 characters")
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Power power = (Power) o;
        return id == power.id && Objects.equals(name, power.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Power{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
