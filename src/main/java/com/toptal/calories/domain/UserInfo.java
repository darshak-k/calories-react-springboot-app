package com.toptal.calories.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A UserInfo.
 */
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @OneToMany(mappedBy = "foodUser")
    @JsonIgnoreProperties(value = { "food", "foodUser" }, allowSetters = true)
    private Set<FoodUser> foodUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public UserInfo userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<FoodUser> getFoodUsers() {
        return this.foodUsers;
    }

    public void setFoodUsers(Set<FoodUser> foodUsers) {
        if (this.foodUsers != null) {
            this.foodUsers.forEach(i -> i.setFoodUser(null));
        }
        if (foodUsers != null) {
            foodUsers.forEach(i -> i.setFoodUser(this));
        }
        this.foodUsers = foodUsers;
    }

    public UserInfo foodUsers(Set<FoodUser> foodUsers) {
        this.setFoodUsers(foodUsers);
        return this;
    }

    public UserInfo addFoodUser(FoodUser foodUser) {
        this.foodUsers.add(foodUser);
        foodUser.setFoodUser(this);
        return this;
    }

    public UserInfo removeFoodUser(FoodUser foodUser) {
        this.foodUsers.remove(foodUser);
        foodUser.setFoodUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfo)) {
            return false;
        }
        return id != null && id.equals(((UserInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
