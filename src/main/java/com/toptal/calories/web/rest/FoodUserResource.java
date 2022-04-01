package com.toptal.calories.web.rest;

import com.toptal.calories.repository.FoodUserRepository;
import com.toptal.calories.service.FoodUserService;
import com.toptal.calories.service.dto.FoodUserDTO;
import com.toptal.calories.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.toptal.calories.domain.FoodUser}.
 */
@RestController
@RequestMapping("/api")
public class FoodUserResource {

    private final Logger log = LoggerFactory.getLogger(FoodUserResource.class);

    private static final String ENTITY_NAME = "foodUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodUserService foodUserService;

    private final FoodUserRepository foodUserRepository;

    public FoodUserResource(FoodUserService foodUserService, FoodUserRepository foodUserRepository) {
        this.foodUserService = foodUserService;
        this.foodUserRepository = foodUserRepository;
    }

    /**
     * {@code POST  /food-users} : Create a new foodUser.
     *
     * @param foodUserDTO the foodUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodUserDTO, or with status {@code 400 (Bad Request)} if the foodUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/food-users")
    public ResponseEntity<FoodUserDTO> createFoodUser(@RequestBody FoodUserDTO foodUserDTO) throws URISyntaxException {
        log.debug("REST request to save FoodUser : {}", foodUserDTO);
        if (foodUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new foodUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodUserDTO result = foodUserService.save(foodUserDTO);
        return ResponseEntity
            .created(new URI("/api/food-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /food-users/:id} : Updates an existing foodUser.
     *
     * @param id the id of the foodUserDTO to save.
     * @param foodUserDTO the foodUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodUserDTO,
     * or with status {@code 400 (Bad Request)} if the foodUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/food-users/{id}")
    public ResponseEntity<FoodUserDTO> updateFoodUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoodUserDTO foodUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FoodUser : {}, {}", id, foodUserDTO);
        if (foodUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FoodUserDTO result = foodUserService.save(foodUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, foodUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /food-users/:id} : Partial updates given fields of an existing foodUser, field will ignore if it is null
     *
     * @param id the id of the foodUserDTO to save.
     * @param foodUserDTO the foodUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodUserDTO,
     * or with status {@code 400 (Bad Request)} if the foodUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the foodUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the foodUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/food-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FoodUserDTO> partialUpdateFoodUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FoodUserDTO foodUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FoodUser partially : {}, {}", id, foodUserDTO);
        if (foodUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FoodUserDTO> result = foodUserService.partialUpdate(foodUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, foodUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /food-users} : get all the foodUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foodUsers in body.
     */
    @GetMapping("/food-users")
    public ResponseEntity<List<FoodUserDTO>> getAllFoodUsers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FoodUsers");
        Page<FoodUserDTO> page = foodUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /food-users/:id} : get the "id" foodUser.
     *
     * @param id the id of the foodUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/food-users/{id}")
    public ResponseEntity<FoodUserDTO> getFoodUser(@PathVariable Long id) {
        log.debug("REST request to get FoodUser : {}", id);
        Optional<FoodUserDTO> foodUserDTO = foodUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodUserDTO);
    }

    /**
     * {@code DELETE  /food-users/:id} : delete the "id" foodUser.
     *
     * @param id the id of the foodUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/food-users/{id}")
    public ResponseEntity<Void> deleteFoodUser(@PathVariable Long id) {
        log.debug("REST request to delete FoodUser : {}", id);
        foodUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
