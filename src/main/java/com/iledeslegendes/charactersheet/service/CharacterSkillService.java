package com.iledeslegendes.charactersheet.service;

import com.iledeslegendes.charactersheet.domain.CharacterSkill;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CharacterSkill}.
 */
public interface CharacterSkillService {
    /**
     * Save a characterSkill.
     *
     * @param characterSkill the entity to save.
     * @return the persisted entity.
     */
    CharacterSkill save(CharacterSkill characterSkill);

    /**
     * Partially updates a characterSkill.
     *
     * @param characterSkill the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CharacterSkill> partialUpdate(CharacterSkill characterSkill);

    /**
     * Get all the characterSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CharacterSkill> findAll(Pageable pageable);

    /**
     * Get the "id" characterSkill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CharacterSkill> findOne(Long id);

    /**
     * Delete the "id" characterSkill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
