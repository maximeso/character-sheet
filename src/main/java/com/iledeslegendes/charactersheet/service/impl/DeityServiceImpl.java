package com.iledeslegendes.charactersheet.service.impl;

import com.iledeslegendes.charactersheet.domain.Deity;
import com.iledeslegendes.charactersheet.repository.DeityRepository;
import com.iledeslegendes.charactersheet.service.DeityService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Deity}.
 */
@Service
@Transactional
public class DeityServiceImpl implements DeityService {

    private final Logger log = LoggerFactory.getLogger(DeityServiceImpl.class);

    private final DeityRepository deityRepository;

    public DeityServiceImpl(DeityRepository deityRepository) {
        this.deityRepository = deityRepository;
    }

    @Override
    public Deity save(Deity deity) {
        log.debug("Request to save Deity : {}", deity);
        return deityRepository.save(deity);
    }

    @Override
    public Optional<Deity> partialUpdate(Deity deity) {
        log.debug("Request to partially update Deity : {}", deity);

        return deityRepository
            .findById(deity.getId())
            .map(
                existingDeity -> {
                    if (deity.getName() != null) {
                        existingDeity.setName(deity.getName());
                    }

                    return existingDeity;
                }
            )
            .map(deityRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Deity> findAll(Pageable pageable) {
        log.debug("Request to get all Deities");
        return deityRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Deity> findOne(Long id) {
        log.debug("Request to get Deity : {}", id);
        return deityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Deity : {}", id);
        deityRepository.deleteById(id);
    }
}
