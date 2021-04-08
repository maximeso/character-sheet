package com.iledeslegendes.charactersheet.service.impl;

import com.iledeslegendes.charactersheet.domain.Career;
import com.iledeslegendes.charactersheet.repository.CareerRepository;
import com.iledeslegendes.charactersheet.service.CareerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Career}.
 */
@Service
@Transactional
public class CareerServiceImpl implements CareerService {

    private final Logger log = LoggerFactory.getLogger(CareerServiceImpl.class);

    private final CareerRepository careerRepository;

    public CareerServiceImpl(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    @Override
    public Career save(Career career) {
        log.debug("Request to save Career : {}", career);
        return careerRepository.save(career);
    }

    @Override
    public Optional<Career> partialUpdate(Career career) {
        log.debug("Request to partially update Career : {}", career);

        return careerRepository
            .findById(career.getId())
            .map(
                existingCareer -> {
                    if (career.getName() != null) {
                        existingCareer.setName(career.getName());
                    }

                    return existingCareer;
                }
            )
            .map(careerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Career> findAll(Pageable pageable) {
        log.debug("Request to get all Careers");
        return careerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Career> findOne(Long id) {
        log.debug("Request to get Career : {}", id);
        return careerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Career : {}", id);
        careerRepository.deleteById(id);
    }
}
