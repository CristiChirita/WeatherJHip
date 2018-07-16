package com.nttdata.weather.service.util.temperatureUtil;

import com.nttdata.weather.domain.Temperature;

import java.util.*;

public class TemperaturePrinter {

    public void getGlobalExtremes(List<WeatherBean> beans) { //Possibly redundant
        WeatherBean minTemp = beans.get(0);
        WeatherBean maxTemp = minTemp;

        for (WeatherBean bean : beans) {
            double beanTemperature = Double.parseDouble(bean.getTemperature().getMetric().get("Value"));
            double minTemperature = Double.parseDouble(minTemp.getTemperature().getMetric().get("Value"));
            double maxTemperature = Double.parseDouble(maxTemp.getTemperature().getMetric().get("Value"));
            if (minTemperature > beanTemperature) {
                minTemp = bean;
            }
            if (maxTemperature < beanTemperature) {
                maxTemp = bean;
            }
        }
        System.out.println("Lowest temperature globally is " + minTemp.getTemperature().getMetric().get("Value") + " in " + minTemp.getEnglishName() + ", " + minTemp.getCountry().getEnglishName());
        System.out.println("Highest temperature globally is " + maxTemp.getTemperature().getMetric().get("Value") + " in " + maxTemp.getEnglishName() + ", " + maxTemp.getCountry().getEnglishName());
    }

    public List<Temperature> getExtremesPerContinent(HashMap<String, ArrayList<WeatherBean>> groupedBeans){

        Iterator it = groupedBeans.entrySet().iterator();
        List<Temperature> list = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String continent = pair.getKey().toString();
            ArrayList<WeatherBean> unpackedBeans = (ArrayList) pair.getValue();
            if (unpackedBeans.size() < 1)
                continue;
            WeatherBean minTemp = unpackedBeans.get(0);
            WeatherBean maxTemp = minTemp;
            int count = 0;
            for (WeatherBean bean : unpackedBeans) {
                double beanTemperature = Double.parseDouble(bean.getTemperature().getMetric().get("Value"));
                double minTemperature = Double.parseDouble(minTemp.getTemperature().getMetric().get("Value"));
                double maxTemperature = Double.parseDouble(maxTemp.getTemperature().getMetric().get("Value"));
                count++;
                if (minTemperature > beanTemperature) {
                    minTemp = bean;
                }
                if (maxTemperature < beanTemperature) {
                    maxTemp = bean;
                }
            }
            Temperature entity = new Temperature().continent(continent);
            entity.minTemperature(Double.parseDouble(minTemp.getTemperature().getMetric().get("Value"))).city(minTemp.getEnglishName()).country(minTemp.getCountry().getEnglishName());
            entity.maxTemperature(Double.parseDouble(maxTemp.getTemperature().getMetric().get("Value"))).cityMax(maxTemp.getEnglishName()).countryMax(maxTemp.getCountry().getEnglishName());
            list.add(entity);
        }
        return list;
    }
}
