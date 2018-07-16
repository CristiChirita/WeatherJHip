package com.nttdata.weather.service.util.temperatureUtil;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class WeatherBean {
    @JsonProperty("Key")
    private String key;
    @JsonProperty("EnglishName")
    private String englishName;
    @JsonProperty("Country")
    private Country country;
    @JsonProperty("TimeZone")
    private TimeZone timeZone;
    @JsonProperty("WeatherText")
    private String weatherText;
    @JsonProperty("Temperature")
    private Temperature temperature;

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public class Country {
        @JsonProperty("ID")
        private String ID;
        @JsonProperty("EnglishName")
        private String englishName;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }
    }

    public class TimeZone{
        @JsonProperty("Name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public class Temperature {
        @JsonProperty("Metric")
        private Map<String, String> metric;

        public Map<String, String> getMetric() {
            return metric;
        }

        public void setMetric(Map<String, String> metric) {
            this.metric = metric;
        }
    }
}
