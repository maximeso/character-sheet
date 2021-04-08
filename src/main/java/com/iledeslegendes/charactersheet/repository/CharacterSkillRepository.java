package com.iledeslegendes.charactersheet.repository;

import com.iledeslegendes.charactersheet.domain.CharacterSkill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CharacterSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CharacterSkillRepository extends JpaRepository<CharacterSkill, Long> {}
