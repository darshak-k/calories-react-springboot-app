package com.toptal.calories.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.toptal.calories.domain.FoodUser} entity.
 */
public class FoodUserDTO implements Serializable {

    private Long id;

    private Instant eatenAt;

    private FoodDTO food;

    private UserInfoDTO foodUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEatenAt() {
        return eatenAt;
    }

    public void setEatenAt(Instant eatenAt) {
        this.eatenAt = eatenAt;
    }

    public FoodDTO getFood() {
        return food;
    }

    public void setFood(FoodDTO food) {
        this.food = food;
    }

    public UserInfoDTO getFoodUser() {
        return foodUser;
    }

    public void setFoodUser(UserInfoDTO foodUser) {
        this.foodUser = foodUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodUserDTO)) {
            return false;
        }

        FoodUserDTO foodUserDTO = (FoodUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, foodUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodUserDTO{" +
            "id=" + getId() +
            ", eatenAt='" + getEatenAt() + "'" +
            ", food=" + getFood() +
            ", foodUser=" + getFoodUser() +
            "}";
    }
}
