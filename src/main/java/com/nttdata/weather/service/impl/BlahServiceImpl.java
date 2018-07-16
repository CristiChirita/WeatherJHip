package com.nttdata.weather.service.impl;

import com.nttdata.weather.service.BlahService;
import com.nttdata.weather.domain.Blah;
import com.nttdata.weather.repository.BlahRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Blah.
 */
@Service
@Transactional
public class BlahServiceImpl implements BlahService {

    private final Logger log = LoggerFactory.getLogger(BlahServiceImpl.class);

    private final BlahRepository blahRepository;

    public BlahServiceImpl(BlahRepository blahRepository) {
        this.blahRepository = blahRepository;
    }

    /**
     * Save a blah.
     *
     * @param blah the entity to save
     * @return the persisted entity
     */
    @Override
    public Blah save(Blah blah) {
        log.debug("Request to save Blah : {}", blah);        return blahRepository.save(blah);
    }

    /**
     * Get all the blahs.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Blah> findAll() {
        log.debug("Request to get all Blahs");
        return blahRepository.findAll();
    }


    /**
     * Get one blah by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Blah> findOne(Long id) {
        log.debug("Request to get Blah : {}", id);
        return blahRepository.findById(id);
    }

    /**
     * Delete the blah by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Blah : {}", id);
        blahRepository.deleteById(id);
    }
}
