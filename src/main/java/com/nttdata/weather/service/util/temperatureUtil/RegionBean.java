package com.nttdata.weather.service.util.temperatureUtil;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegionBean {
    @JsonProperty("Key")
    private String key;
    @JsonProperty("EnglishName")
    private String englishName;
    @JsonProperty("Region")
    private Region region;

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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public class Region {
        @JsonProperty("EnglishName")
        private String englishName;

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }
    }
}

