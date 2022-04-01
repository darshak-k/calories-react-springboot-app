package com.toptal.calories.repository;

import com.toptal.calories.domain.FoodUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FoodUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodUserRepository extends JpaRepository<FoodUser, Long> {}
