package com.toptal.calories.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.toptal.calories.domain.Food} entity.
 */
public class FoodDTO implements Serializable {

    private Long id;

    @NotNull
    private String foodName;

    private Long calorieValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Long getCalorieValue() {
        return calorieValue;
    }

    public void setCalorieValue(Long calorieValue) {
        this.calorieValue = calorieValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodDTO)) {
            return false;
        }

        FoodDTO foodDTO = (FoodDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, foodDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodDTO{" +
            "id=" + getId() +
            ", foodName='" + getFoodName() + "'" +
            ", calorieValue=" + getCalorieValue() +
            "}";
    }
}
