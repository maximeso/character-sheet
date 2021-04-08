package com.iledeslegendes.charactersheet.service;

import com.iledeslegendes.charactersheet.domain.Career;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Career}.
 */
public interface CareerService {
    /**
     * Save a career.
     *
     * @param career the entity to save.
     * @return the persisted entity.
     */
    Career save(Career career);

    /**
     * Partially updates a career.
     *
     * @param career the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Career> partialUpdate(Career career);

    /**
     * Get all the careers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Career> findAll(Pageable pageable);

    /**
     * Get the "id" career.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Career> findOne(Long id);

    /**
     * Delete the "id" career.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
