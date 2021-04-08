package com.iledeslegendes.charactersheet.web.rest;

import com.iledeslegendes.charactersheet.domain.Deity;
import com.iledeslegendes.charactersheet.repository.DeityRepository;
import com.iledeslegendes.charactersheet.service.DeityService;
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
 * REST controller for managing {@link com.iledeslegendes.charactersheet.domain.Deity}.
 */
@RestController
@RequestMapping("/api")
public class DeityResource {

    private final Logger log = LoggerFactory.getLogger(DeityResource.class);

    private static final String ENTITY_NAME = "deity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeityService deityService;

    private final DeityRepository deityRepository;

    public DeityResource(DeityService deityService, DeityRepository deityRepository) {
        this.deityService = deityService;
        this.deityRepository = deityRepository;
    }

    /**
     * {@code POST  /deities} : Create a new deity.
     *
     * @param deity the deity to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deity, or with status {@code 400 (Bad Request)} if the deity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deities")
    public ResponseEntity<Deity> createDeity(@RequestBody Deity deity) throws URISyntaxException {
        log.debug("REST request to save Deity : {}", deity);
        if (deity.getId() != null) {
            throw new BadRequestAlertException("A new deity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deity result = deityService.save(deity);
        return ResponseEntity
            .created(new URI("/api/deities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deities/:id} : Updates an existing deity.
     *
     * @param id the id of the deity to save.
     * @param deity the deity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deity,
     * or with status {@code 400 (Bad Request)} if the deity is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deities/{id}")
    public ResponseEntity<Deity> updateDeity(@PathVariable(value = "id", required = false) final Long id, @RequestBody Deity deity)
        throws URISyntaxException {
        log.debug("REST request to update Deity : {}, {}", id, deity);
        if (deity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Deity result = deityService.save(deity);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deity.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /deities/:id} : Partial updates given fields of an existing deity, field will ignore if it is null
     *
     * @param id the id of the deity to save.
     * @param deity the deity to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deity,
     * or with status {@code 400 (Bad Request)} if the deity is not valid,
     * or with status {@code 404 (Not Found)} if the deity is not found,
     * or with status {@code 500 (Internal Server Error)} if the deity couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/deities/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Deity> partialUpdateDeity(@PathVariable(value = "id", required = false) final Long id, @RequestBody Deity deity)
        throws URISyntaxException {
        log.debug("REST request to partial update Deity partially : {}, {}", id, deity);
        if (deity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, deity.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!deityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Deity> result = deityService.partialUpdate(deity);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deity.getId().toString())
        );
    }

    /**
     * {@code GET  /deities} : get all the deities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deities in body.
     */
    @GetMapping("/deities")
    public ResponseEntity<List<Deity>> getAllDeities(Pageable pageable) {
        log.debug("REST request to get a page of Deities");
        Page<Deity> page = deityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /deities/:id} : get the "id" deity.
     *
     * @param id the id of the deity to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deity, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deities/{id}")
    public ResponseEntity<Deity> getDeity(@PathVariable Long id) {
        log.debug("REST request to get Deity : {}", id);
        Optional<Deity> deity = deityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deity);
    }

    /**
     * {@code DELETE  /deities/:id} : delete the "id" deity.
     *
     * @param id the id of the deity to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deities/{id}")
    public ResponseEntity<Void> deleteDeity(@PathVariable Long id) {
        log.debug("REST request to delete Deity : {}", id);
        deityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
