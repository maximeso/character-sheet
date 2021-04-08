package com.iledeslegendes.charactersheet.service.impl;

import com.iledeslegendes.charactersheet.domain.Character;
import com.iledeslegendes.charactersheet.repository.CharacterRepository;
import com.iledeslegendes.charactersheet.service.CharacterService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Character}.
 */
@Service
@Transactional
public class CharacterServiceImpl implements CharacterService {

    private final Logger log = LoggerFactory.getLogger(CharacterServiceImpl.class);

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Character save(Character character) {
        log.debug("Request to save Character : {}", character);
        return characterRepository.save(character);
    }

    @Override
    public Optional<Character> partialUpdate(Character character) {
        log.debug("Request to partially update Character : {}", character);

        return characterRepository
            .findById(character.getId())
            .map(
                existingCharacter -> {
                    if (character.getName() != null) {
                        existingCharacter.setName(character.getName());
                    }
                    if (character.getAlignment() != null) {
                        existingCharacter.setAlignment(character.getAlignment());
                    }
                    if (character.getExperience() != null) {
                        existingCharacter.setExperience(character.getExperience());
                    }
                    if (character.getParty() != null) {
                        existingCharacter.setParty(character.getParty());
                    }

                    return existingCharacter;
                }
            )
            .map(characterRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Character> findAll(Pageable pageable) {
        log.debug("Request to get all Characters");
        return characterRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Character> findOne(Long id) {
        log.debug("Request to get Character : {}", id);
        return characterRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Character : {}", id);
        characterRepository.deleteById(id);
    }
}
