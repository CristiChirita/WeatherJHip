package com.nttdata.weather.service.util.temperatureUtil;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONParser {
    public List<WeatherBean> parseJSON(String in){
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JavaType type = mapper.getTypeFactory().
                constructCollectionType(List.class, WeatherBean.class);
            List<WeatherBean> bean = mapper.readValue(in, type);
            return bean;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RegionBean> parseRegionData(String in){
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JavaType type = mapper.getTypeFactory().
                constructCollectionType(List.class, RegionBean.class);
            List<RegionBean> regionBeans = mapper.readValue(in, type);
            return regionBeans;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, ArrayList<WeatherBean>> groupByContinent(List<WeatherBean> beans, List<RegionBean> regionBeans) {
        HashMap<String, ArrayList<WeatherBean>> map = new HashMap<String, ArrayList<WeatherBean>>();
        map.put("Europe", new ArrayList<WeatherBean>());
        map.put("Africa", new ArrayList<WeatherBean>());
        map.put("Asia", new ArrayList<WeatherBean>());
        map.put("North America", new ArrayList<WeatherBean>());
        map.put("South America", new ArrayList<WeatherBean>());
        map.put("Oceania", new ArrayList<WeatherBean>());
        map.put("Antarctica", new ArrayList<WeatherBean>());

        for (WeatherBean bean : beans) {
            String region = "";
            for (RegionBean regionBean : regionBeans) {
                if (bean.getKey().equals(regionBean.getKey())) {
                    region = regionBean.getRegion().getEnglishName();
                }
            }
            if (region.equals("Europe")) {
                map.get("Europe").add(bean);
            }
            if (region.equals("Africa")) {
                map.get("Africa").add(bean);
            }
            if (region.equals("Asia") || region.equals("Middle East")) {
                map.get("Asia").add(bean);
            }
            if (region.equals("North America") || region.equals("Central America")) {
                map.get("North America").add(bean);
            }
            if (region.equals("South America")) {
                map.get("South America").add(bean);
            }
            if (region.equals("Oceania")) {
                map.get("Oceania").add(bean);
            }
            if (region.equals("Antarctica")) {
                map.get("Antarctica").add(bean);
            }
        }

        return map;
    }
}
