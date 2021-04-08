package com.iledeslegendes.charactersheet.service;

import com.iledeslegendes.charactersheet.domain.Race;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Race}.
 */
public interface RaceService {
    /**
     * Save a race.
     *
     * @param race the entity to save.
     * @return the persisted entity.
     */
    Race save(Race race);

    /**
     * Partially updates a race.
     *
     * @param race the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Race> partialUpdate(Race race);

    /**
     * Get all the races.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Race> findAll(Pageable pageable);

    /**
     * Get the "id" race.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Race> findOne(Long id);

    /**
     * Delete the "id" race.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
