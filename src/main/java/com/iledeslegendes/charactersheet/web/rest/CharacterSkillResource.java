package com.iledeslegendes.charactersheet.web.rest;

import com.iledeslegendes.charactersheet.domain.CharacterSkill;
import com.iledeslegendes.charactersheet.repository.CharacterSkillRepository;
import com.iledeslegendes.charactersheet.service.CharacterSkillService;
import com.iledeslegendes.charactersheet.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.iledeslegendes.charactersheet.domain.CharacterSkill}.
 */
@RestController
@RequestMapping("/api")
public class CharacterSkillResource {

    private final Logger log = LoggerFactory.getLogger(CharacterSkillResource.class);

    private static final String ENTITY_NAME = "characterSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CharacterSkillService characterSkillService;

    private final CharacterSkillRepository characterSkillRepository;

    public CharacterSkillResource(CharacterSkillService characterSkillService, CharacterSkillRepository characterSkillRepository) {
        this.characterSkillService = characterSkillService;
        this.characterSkillRepository = characterSkillRepository;
    }

    /**
     * {@code POST  /character-skills} : Create a new characterSkill.
     *
     * @param characterSkill the characterSkill to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new characterSkill, or with status {@code 400 (Bad Request)} if the characterSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/character-skills")
    public ResponseEntity<CharacterSkill> createCharacterSkill(@RequestBody CharacterSkill characterSkill) throws URISyntaxException {
        log.debug("REST request to save CharacterSkill : {}", characterSkill);
        if (characterSkill.getId() != null) {
            throw new BadRequestAlertException("A new characterSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CharacterSkill result = characterSkillService.save(characterSkill);
        return ResponseEntity
            .created(new URI("/api/character-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /character-skills/:id} : Updates an existing characterSkill.
     *
     * @param id the id of the characterSkill to save.
     * @param characterSkill the characterSkill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated characterSkill,
     * or with status {@code 400 (Bad Request)} if the characterSkill is not valid,
     * or with status {@code 500 (Internal Server Error)} if the characterSkill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/character-skills/{id}")
    public ResponseEntity<CharacterSkill> updateCharacterSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CharacterSkill characterSkill
    ) throws URISyntaxException {
        log.debug("REST request to update CharacterSkill : {}, {}", id, characterSkill);
        if (characterSkill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, characterSkill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characterSkillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CharacterSkill result = characterSkillService.save(characterSkill);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, characterSkill.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /character-skills/:id} : Partial updates given fields of an existing characterSkill, field will ignore if it is null
     *
     * @param id the id of the characterSkill to save.
     * @param characterSkill the characterSkill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated characterSkill,
     * or with status {@code 400 (Bad Request)} if the characterSkill is not valid,
     * or with status {@code 404 (Not Found)} if the characterSkill is not found,
     * or with status {@code 500 (Internal Server Error)} if the characterSkill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/character-skills/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CharacterSkill> partialUpdateCharacterSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CharacterSkill characterSkill
    ) throws URISyntaxException {
        log.debug("REST request to partial update CharacterSkill partially : {}, {}", id, characterSkill);
        if (characterSkill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, characterSkill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!characterSkillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CharacterSkill> result = characterSkillService.partialUpdate(characterSkill);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, characterSkill.getId().toString())
        );
    }

    /**
     * {@code GET  /character-skills} : get all the characterSkills.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of characterSkills in body.
     */
    @GetMapping("/character-skills")
    public ResponseEntity<List<CharacterSkill>> getAllCharacterSkills(Pageable pageable) {
        log.debug("REST request to get a page of CharacterSkills");
        Page<CharacterSkill> page = characterSkillService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /character-skills/:id} : get the "id" characterSkill.
     *
     * @param id the id of the characterSkill to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the characterSkill, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/character-skills/{id}")
    public ResponseEntity<CharacterSkill> getCharacterSkill(@PathVariable Long id) {
        log.debug("REST request to get CharacterSkill : {}", id);
        Optional<CharacterSkill> characterSkill = characterSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(characterSkill);
    }

    /**
     * {@code DELETE  /character-skills/:id} : delete the "id" characterSkill.
     *
     * @param id the id of the characterSkill to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/character-skills/{id}")
    public ResponseEntity<Void> deleteCharacterSkill(@PathVariable Long id) {
        log.debug("REST request to delete CharacterSkill : {}", id);
        characterSkillService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
