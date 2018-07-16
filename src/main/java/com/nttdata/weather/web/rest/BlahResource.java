package com.nttdata.weather.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.nttdata.weather.domain.Blah;
import com.nttdata.weather.service.BlahService;
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
 * REST controller for managing Blah.
 */
@RestController
@RequestMapping("/api")
public class BlahResource {

    private final Logger log = LoggerFactory.getLogger(BlahResource.class);

    private static final String ENTITY_NAME = "blah";

    private final BlahService blahService;

    public BlahResource(BlahService blahService) {
        this.blahService = blahService;
    }

    /**
     * POST  /blahs : Create a new blah.
     *
     * @param blah the blah to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blah, or with status 400 (Bad Request) if the blah has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blahs")
    @Timed
    public ResponseEntity<Blah> createBlah(@RequestBody Blah blah) throws URISyntaxException {
        log.debug("REST request to save Blah : {}", blah);
        if (blah.getId() != null) {
            throw new BadRequestAlertException("A new blah cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Blah result = blahService.save(blah);
        return ResponseEntity.created(new URI("/api/blahs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blahs : Updates an existing blah.
     *
     * @param blah the blah to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blah,
     * or with status 400 (Bad Request) if the blah is not valid,
     * or with status 500 (Internal Server Error) if the blah couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blahs")
    @Timed
    public ResponseEntity<Blah> updateBlah(@RequestBody Blah blah) throws URISyntaxException {
        log.debug("REST request to update Blah : {}", blah);
        if (blah.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Blah result = blahService.save(blah);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, blah.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blahs : get all the blahs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of blahs in body
     */
    @GetMapping("/blahs")
    @Timed
    public List<Blah> getAllBlahs() {
        log.debug("REST request to get all Blahs");
        return blahService.findAll();
    }

    /**
     * GET  /blahs/:id : get the "id" blah.
     *
     * @param id the id of the blah to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blah, or with status 404 (Not Found)
     */
    @GetMapping("/blahs/{id}")
    @Timed
    public ResponseEntity<Blah> getBlah(@PathVariable Long id) {
        log.debug("REST request to get Blah : {}", id);
        Optional<Blah> blah = blahService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blah);
    }

    /**
     * DELETE  /blahs/:id : delete the "id" blah.
     *
     * @param id the id of the blah to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blahs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBlah(@PathVariable Long id) {
        log.debug("REST request to delete Blah : {}", id);
        blahService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
