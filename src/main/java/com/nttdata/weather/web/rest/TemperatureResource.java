package com.nttdata.weather.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.weather.domain.Temperature;
import com.nttdata.weather.service.TemperatureService;
import com.nttdata.weather.web.rest.errors.BadRequestAlertException;
import com.nttdata.weather.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Temperature.
 */
@RestController
@RequestMapping("/api")
public class TemperatureResource {

    private final Logger log = LoggerFactory.getLogger(TemperatureResource.class);

    private static final String ENTITY_NAME = "temperature";

    private final TemperatureService temperatureService;

    public TemperatureResource(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    /**
     * POST  /temperatures : Create a new temperature.
     *
     * @param temperature the temperature to create
     * @return the ResponseEntity with status 201 (Created) and with body the new temperature, or with status 400 (Bad Request) if the temperature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/temperatures")
    @Timed
    public ResponseEntity<Temperature> createTemperature(@RequestBody Temperature temperature) throws URISyntaxException {
        log.debug("REST request to save Temperature : {}", temperature);
        if (temperature.getId() != null) {
            throw new BadRequestAlertException("A new temperature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Temperature result = temperatureService.save(temperature);
        return ResponseEntity.created(new URI("/api/temperatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /temperatures : Updates an existing temperature.
     *
     * @param temperature the temperature to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated temperature,
     * or with status 400 (Bad Request) if the temperature is not valid,
     * or with status 500 (Internal Server Error) if the temperature couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/temperatures")
    @Timed
    public ResponseEntity<Temperature> updateTemperature(@RequestBody Temperature temperature) throws URISyntaxException {
        log.debug("REST request to update Temperature : {}", temperature);
        if (temperature.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Temperature result = temperatureService.save(temperature);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, temperature.getId().toString()))
            .body(result);
    }

    /**
     * GET  /temperatures : get all the temperatures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of temperatures in body
     */
    @GetMapping("/temperatures")
    @Timed
    public List<Temperature> getAllTemperatures() {
        log.debug("REST request to get all Temperatures");
        return temperatureService.findAll();
    }

    /**
     * GET  /temperatures/:id : get the "id" temperature.
     *
     * @param id the id of the temperature to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the temperature, or with status 404 (Not Found)
     */
    @GetMapping("/temperatures/{id}")
    @Timed
    public ResponseEntity<Temperature> getTemperature(@PathVariable Long id) {
        log.debug("REST request to get Temperature : {}", id);
        Optional<Temperature> temperature = temperatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(temperature);
    }

    /**
     * DELETE  /temperatures/:id : delete the "id" temperature.
     *
     * @param id the id of the temperature to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/temperatures/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemperature(@PathVariable Long id) {
        log.debug("REST request to delete Temperature : {}", id);
        temperatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
