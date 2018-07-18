package com.nttdata.weather.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Temperature.
 */
@Entity
@Table(name = "temperature")
public class Temperature implements Serializable, Comparable<Temperature> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "continent")
    private String continent;

    @Column(name = "min_temperature")
    private Double minTemperature;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "max_temperature")
    private Double maxTemperature;

    @Column(name = "city_max")
    private String cityMax;

    @Column(name = "country_max")
    private String countryMax;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContinent() {
        return continent;
    }

    public Temperature continent(String continent) {
        this.continent = continent;
        return this;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public Temperature minTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
        return this;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getCity() {
        return city;
    }

    public Temperature city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public Temperature country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public Temperature maxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
        return this;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getCityMax() {
        return cityMax;
    }

    public Temperature cityMax(String cityMax) {
        this.cityMax = cityMax;
        return this;
    }

    public void setCityMax(String cityMax) {
        this.cityMax = cityMax;
    }

    public String getCountryMax() {
        return countryMax;
    }

    public Temperature countryMax(String countryMax) {
        this.countryMax = countryMax;
        return this;
    }

    public void setCountryMax(String countryMax) {
        this.countryMax = countryMax;
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
        Temperature temperature = (Temperature) o;
        if (temperature.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), temperature.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Temperature{" +
            "id=" + getId() +
            ", continent='" + getContinent() + "'" +
            ", minTemperature=" + getMinTemperature() +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", maxTemperature=" + getMaxTemperature() +
            ", cityMax='" + getCityMax() + "'" +
            ", countryMax='" + getCountryMax() + "'" +
            "}";
    }

    @Override
    public int compareTo(Temperature other) {
        return continent.compareTo(other.continent);
    }
}
