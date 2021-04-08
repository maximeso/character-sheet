package com.iledeslegendes.charactersheet.service.impl;

import com.iledeslegendes.charactersheet.domain.CharacterSkill;
import com.iledeslegendes.charactersheet.repository.CharacterSkillRepository;
import com.iledeslegendes.charactersheet.service.CharacterSkillService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CharacterSkill}.
 */
@Service
@Transactional
public class CharacterSkillServiceImpl implements CharacterSkillService {

    private final Logger log = LoggerFactory.getLogger(CharacterSkillServiceImpl.class);

    private final CharacterSkillRepository characterSkillRepository;

    public CharacterSkillServiceImpl(CharacterSkillRepository characterSkillRepository) {
        this.characterSkillRepository = characterSkillRepository;
    }

    @Override
    public CharacterSkill save(CharacterSkill characterSkill) {
        log.debug("Request to save CharacterSkill : {}", characterSkill);
        return characterSkillRepository.save(characterSkill);
    }

    @Override
    public Optional<CharacterSkill> partialUpdate(CharacterSkill characterSkill) {
        log.debug("Request to partially update CharacterSkill : {}", characterSkill);

        return characterSkillRepository
            .findById(characterSkill.getId())
            .map(
                existingCharacterSkill -> {
                    if (characterSkill.getEvent() != null) {
                        existingCharacterSkill.setEvent(characterSkill.getEvent());
                    }
                    if (characterSkill.getRealCost() != null) {
                        existingCharacterSkill.setRealCost(characterSkill.getRealCost());
                    }

                    return existingCharacterSkill;
                }
            )
            .map(characterSkillRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CharacterSkill> findAll(Pageable pageable) {
        log.debug("Request to get all CharacterSkills");
        return characterSkillRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CharacterSkill> findOne(Long id) {
        log.debug("Request to get CharacterSkill : {}", id);
        return characterSkillRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CharacterSkill : {}", id);
        characterSkillRepository.deleteById(id);
    }
}
