package com.toptal.calories.service;

import com.toptal.calories.service.dto.FoodUserDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.toptal.calories.domain.FoodUser}.
 */
public interface FoodUserService {
    /**
     * Save a foodUser.
     *
     * @param foodUserDTO the entity to save.
     * @return the persisted entity.
     */
    FoodUserDTO save(FoodUserDTO foodUserDTO);

    /**
     * Partially updates a foodUser.
     *
     * @param foodUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FoodUserDTO> partialUpdate(FoodUserDTO foodUserDTO);

    /**
     * Get all the foodUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FoodUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" foodUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FoodUserDTO> findOne(Long id);

    /**
     * Delete the "id" foodUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
