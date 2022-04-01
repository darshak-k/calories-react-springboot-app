package com.toptal.calories.service.impl;

import com.toptal.calories.domain.Food;
import com.toptal.calories.repository.FoodRepository;
import com.toptal.calories.service.FoodService;
import com.toptal.calories.service.dto.FoodDTO;
import com.toptal.calories.service.mapper.FoodMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Food}.
 */
@Service
@Transactional
public class FoodServiceImpl implements FoodService {

    private final Logger log = LoggerFactory.getLogger(FoodServiceImpl.class);

    private final FoodRepository foodRepository;

    private final FoodMapper foodMapper;

    public FoodServiceImpl(FoodRepository foodRepository, FoodMapper foodMapper) {
        this.foodRepository = foodRepository;
        this.foodMapper = foodMapper;
    }

    @Override
    public FoodDTO save(FoodDTO foodDTO) {
        log.debug("Request to save Food : {}", foodDTO);
        Food food = foodMapper.toEntity(foodDTO);
        food = foodRepository.save(food);
        return foodMapper.toDto(food);
    }

    @Override
    public Optional<FoodDTO> partialUpdate(FoodDTO foodDTO) {
        log.debug("Request to partially update Food : {}", foodDTO);

        return foodRepository
            .findById(foodDTO.getId())
            .map(existingFood -> {
                foodMapper.partialUpdate(existingFood, foodDTO);

                return existingFood;
            })
            .map(foodRepository::save)
            .map(foodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FoodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Foods");
        return foodRepository.findAll(pageable).map(foodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FoodDTO> findOne(Long id) {
        log.debug("Request to get Food : {}", id);
        return foodRepository.findById(id).map(foodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Food : {}", id);
        foodRepository.deleteById(id);
    }
}
