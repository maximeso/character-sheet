package com.iledeslegendes.charactersheet.repository;

import com.iledeslegendes.charactersheet.domain.Deity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Deity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeityRepository extends JpaRepository<Deity, Long> {}
