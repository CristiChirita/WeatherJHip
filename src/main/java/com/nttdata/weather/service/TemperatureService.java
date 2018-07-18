package com.nttdata.weather.service;

import com.nttdata.weather.domain.Temperature;
import com.nttdata.weather.repository.TemperatureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nttdata.weather.service.util.temperatureUtil.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

/**
 * Service Implementation for managing Temperature.
 */
@Service
@Transactional
public class TemperatureService {

    private final Logger log = LoggerFactory.getLogger(TemperatureService.class);

    private final TemperatureRepository temperatureRepository;

    public TemperatureService(TemperatureRepository temperatureRepository) {
        this.temperatureRepository = temperatureRepository;
    }

    /**
     * Save a temperature.
     *
     * @param temperature the entity to save
     * @return the persisted entity
     */
    public Temperature save(Temperature temperature) {
        log.debug("Request to save Temperature : {}", temperature);
        return temperatureRepository.save(temperature);
    }

    /**
     * Get all the temperatures.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Temperature> findAll() {
        log.debug("Request to get all Temperatures");
        JSONParser parser = new JSONParser();
        String apiKey = "jKoDjpA4XoaueBd8j8LHVDfeJT72ACHR"; //Yes, I know this is awful
        String weatherResponse = new HttpClient().resolveRequest(apiKey, "currentconditions");
        String regionData = new HttpClient().resolveRequest(apiKey, "locations");
        List<WeatherBean> beans = new ArrayList<>();
        final List<RegionBean> regionBeans = new ArrayList<>();
        List<String> continents = new ArrayList<>();
        continents.add("Europe");
        continents.add("Asia");
        continents.add("Africa");
        continents.add("North America");
        continents.add("South America");
        continents.add("Antarctica");
        continents.add("Oceania");

        if (weatherResponse != null) {
            beans = parser.parseJSON(weatherResponse);
        }

        if (regionData != null) {
            regionBeans.addAll(parser.parseRegionData(regionData));

        }

        TemperaturePrinter printer = new TemperaturePrinter();
        HashMap<String, ArrayList<WeatherBean>> groupedBeans;
        groupedBeans = parser.groupByContinent(beans, regionBeans);
        List<Temperature> list = printer.getExtremesPerContinent(groupedBeans);
        //Collections.sort(list);
        return list;
        //printer.getGlobalExtremes(beans);
        // return temperatureRepository.findAll();
    }


    /**
     * Get one temperature by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Temperature> findOne(Long id) {
        log.debug("Request to get Temperature : {}", id);
        return temperatureRepository.findById(id);
    }

    /**
     * Delete the temperature by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Temperature : {}", id);
        temperatureRepository.deleteById(id);
    }
}
