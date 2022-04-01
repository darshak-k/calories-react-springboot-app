package com.toptal.calories.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A FoodUser.
 */
@Entity
@Table(name = "food_user")
public class FoodUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "eaten_at")
    private Instant eatenAt;

    @OneToOne
    @JoinColumn(unique = true)
    private Food food;

    @ManyToOne
    @JsonIgnoreProperties(value = { "foodUsers" }, allowSetters = true)
    private UserInfo foodUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FoodUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEatenAt() {
        return this.eatenAt;
    }

    public FoodUser eatenAt(Instant eatenAt) {
        this.setEatenAt(eatenAt);
        return this;
    }

    public void setEatenAt(Instant eatenAt) {
        this.eatenAt = eatenAt;
    }

    public Food getFood() {
        return this.food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public FoodUser food(Food food) {
        this.setFood(food);
        return this;
    }

    public UserInfo getFoodUser() {
        return this.foodUser;
    }

    public void setFoodUser(UserInfo userInfo) {
        this.foodUser = userInfo;
    }

    public FoodUser foodUser(UserInfo userInfo) {
        this.setFoodUser(userInfo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FoodUser)) {
            return false;
        }
        return id != null && id.equals(((FoodUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FoodUser{" +
            "id=" + getId() +
            ", eatenAt='" + getEatenAt() + "'" +
            "}";
    }
}
