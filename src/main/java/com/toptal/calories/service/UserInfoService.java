package com.toptal.calories.service;

import com.toptal.calories.service.dto.UserInfoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.toptal.calories.domain.UserInfo}.
 */
public interface UserInfoService {
    /**
     * Save a userInfo.
     *
     * @param userInfoDTO the entity to save.
     * @return the persisted entity.
     */
    UserInfoDTO save(UserInfoDTO userInfoDTO);

    /**
     * Partially updates a userInfo.
     *
     * @param userInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserInfoDTO> partialUpdate(UserInfoDTO userInfoDTO);

    /**
     * Get all the userInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserInfoDTO> findOne(Long id);

    /**
     * Delete the "id" userInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
