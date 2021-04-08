package com.iledeslegendes.charactersheet.service.impl;

import com.iledeslegendes.charactersheet.domain.Race;
import com.iledeslegendes.charactersheet.repository.RaceRepository;
import com.iledeslegendes.charactersheet.service.RaceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Race}.
 */
@Service
@Transactional
public class RaceServiceImpl implements RaceService {

    private final Logger log = LoggerFactory.getLogger(RaceServiceImpl.class);

    private final RaceRepository raceRepository;

    public RaceServiceImpl(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Race save(Race race) {
        log.debug("Request to save Race : {}", race);
        return raceRepository.save(race);
    }

    @Override
    public Optional<Race> partialUpdate(Race race) {
        log.debug("Request to partially update Race : {}", race);

        return raceRepository
            .findById(race.getId())
            .map(
                existingRace -> {
                    if (race.getName() != null) {
                        existingRace.setName(race.getName());
                    }

                    return existingRace;
                }
            )
            .map(raceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Race> findAll(Pageable pageable) {
        log.debug("Request to get all Races");
        return raceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Race> findOne(Long id) {
        log.debug("Request to get Race : {}", id);
        return raceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Race : {}", id);
        raceRepository.deleteById(id);
    }
}
