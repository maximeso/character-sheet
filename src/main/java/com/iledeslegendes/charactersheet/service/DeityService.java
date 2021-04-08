package com.iledeslegendes.charactersheet.service;

import com.iledeslegendes.charactersheet.domain.Deity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Deity}.
 */
public interface DeityService {
    /**
     * Save a deity.
     *
     * @param deity the entity to save.
     * @return the persisted entity.
     */
    Deity save(Deity deity);

    /**
     * Partially updates a deity.
     *
     * @param deity the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Deity> partialUpdate(Deity deity);

    /**
     * Get all the deities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Deity> findAll(Pageable pageable);

    /**
     * Get the "id" deity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Deity> findOne(Long id);

    /**
     * Delete the "id" deity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
