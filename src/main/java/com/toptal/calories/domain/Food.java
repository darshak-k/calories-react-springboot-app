package com.toptal.calories.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Food.
 */
@Entity
@Table(name = "food")
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(name = "calorie_value")
    private Long calorieValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Food id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public Food foodName(String foodName) {
        this.setFoodName(foodName);
        return this;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Long getCalorieValue() {
        return this.calorieValue;
    }

    public Food calorieValue(Long calorieValue) {
        this.setCalorieValue(calorieValue);
        return this;
    }

    public void setCalorieValue(Long calorieValue) {
        this.calorieValue = calorieValue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Food)) {
            return false;
        }
        return id != null && id.equals(((Food) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Food{" +
            "id=" + getId() +
            ", foodName='" + getFoodName() + "'" +
            ", calorieValue=" + getCalorieValue() +
            "}";
    }
}
