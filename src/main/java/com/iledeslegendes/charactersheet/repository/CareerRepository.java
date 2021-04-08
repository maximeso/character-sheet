package com.iledeslegendes.charactersheet.repository;

import com.iledeslegendes.charactersheet.domain.Career;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Career entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {}
