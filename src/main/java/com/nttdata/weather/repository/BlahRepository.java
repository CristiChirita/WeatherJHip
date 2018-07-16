package com.nttdata.weather.repository;

import com.nttdata.weather.domain.Blah;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Blah entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlahRepository extends JpaRepository<Blah, Long> {

}
