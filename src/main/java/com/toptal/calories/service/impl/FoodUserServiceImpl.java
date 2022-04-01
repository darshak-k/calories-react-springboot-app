package com.toptal.calories.service.impl;

import com.toptal.calories.domain.FoodUser;
import com.toptal.calories.repository.FoodUserRepository;
import com.toptal.calories.service.FoodUserService;
import com.toptal.calories.service.dto.FoodUserDTO;
import com.toptal.calories.service.mapper.FoodUserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FoodUser}.
 */
@Service
@Transactional
public class FoodUserServiceImpl implements FoodUserService {

    private final Logger log = LoggerFactory.getLogger(FoodUserServiceImpl.class);

    private final FoodUserRepository foodUserRepository;

    private final FoodUserMapper foodUserMapper;

    public FoodUserServiceImpl(FoodUserRepository foodUserRepository, FoodUserMapper foodUserMapper) {
        this.foodUserRepository = foodUserRepository;
        this.foodUserMapper = foodUserMapper;
    }

    @Override
    public FoodUserDTO save(FoodUserDTO foodUserDTO) {
        log.debug("Request to save FoodUser : {}", foodUserDTO);
        FoodUser foodUser = foodUserMapper.toEntity(foodUserDTO);
        foodUser = foodUserRepository.save(foodUser);
        return foodUserMapper.toDto(foodUser);
    }

    @Override
    public Optional<FoodUserDTO> partialUpdate(FoodUserDTO foodUserDTO) {
        log.debug("Request to partially update FoodUser : {}", foodUserDTO);

        return foodUserRepository
            .findById(foodUserDTO.getId())
            .map(existingFoodUser -> {
                foodUserMapper.partialUpdate(existingFoodUser, foodUserDTO);

                return existingFoodUser;
            })
            .map(foodUserRepository::save)
            .map(foodUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FoodUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FoodUsers");
        return foodUserRepository.findAll(pageable).map(foodUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FoodUserDTO> findOne(Long id) {
        log.debug("Request to get FoodUser : {}", id);
        return foodUserRepository.findById(id).map(foodUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FoodUser : {}", id);
        foodUserRepository.deleteById(id);
    }
}
