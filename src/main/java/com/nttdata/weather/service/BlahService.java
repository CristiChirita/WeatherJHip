package com.nttdata.weather.service;

import com.nttdata.weather.domain.Blah;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Blah.
 */
public interface BlahService {

    /**
     * Save a blah.
     *
     * @param blah the entity to save
     * @return the persisted entity
     */
    Blah save(Blah blah);

    /**
     * Get all the blahs.
     *
     * @return the list of entities
     */
    List<Blah> findAll();


    /**
     * Get the "id" blah.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Blah> findOne(Long id);

    /**
     * Delete the "id" blah.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
