package com.nttdata.weather.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Blah.
 */
@Entity
@Table(name = "blah")
public class Blah implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "continent")
    private String continent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public Blah location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContinent() {
        return continent;
    }

    public Blah continent(String continent) {
        this.continent = continent;
        return this;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Blah blah = (Blah) o;
        if (blah.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), blah.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Blah{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            ", continent='" + getContinent() + "'" +
            "}";
    }
}
