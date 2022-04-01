package com.toptal.calories.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toptal.calories.IntegrationTest;
import com.toptal.calories.domain.FoodUser;
import com.toptal.calories.repository.FoodUserRepository;
import com.toptal.calories.service.dto.FoodUserDTO;
import com.toptal.calories.service.mapper.FoodUserMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FoodUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FoodUserResourceIT {

    private static final Instant DEFAULT_EATEN_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EATEN_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/food-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FoodUserRepository foodUserRepository;

    @Autowired
    private FoodUserMapper foodUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoodUserMockMvc;

    private FoodUser foodUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodUser createEntity(EntityManager em) {
        FoodUser foodUser = new FoodUser().eatenAt(DEFAULT_EATEN_AT);
        return foodUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FoodUser createUpdatedEntity(EntityManager em) {
        FoodUser foodUser = new FoodUser().eatenAt(UPDATED_EATEN_AT);
        return foodUser;
    }

    @BeforeEach
    public void initTest() {
        foodUser = createEntity(em);
    }

    @Test
    @Transactional
    void createFoodUser() throws Exception {
        int databaseSizeBeforeCreate = foodUserRepository.findAll().size();
        // Create the FoodUser
        FoodUserDTO foodUserDTO = foodUserMapper.toDto(foodUser);
        restFoodUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodUserDTO)))
            .andExpect(status().isCreated());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeCreate + 1);
        FoodUser testFoodUser = foodUserList.get(foodUserList.size() - 1);
        assertThat(testFoodUser.getEatenAt()).isEqualTo(DEFAULT_EATEN_AT);
    }

    @Test
    @Transactional
    void createFoodUserWithExistingId() throws Exception {
        // Create the FoodUser with an existing ID
        foodUser.setId(1L);
        FoodUserDTO foodUserDTO = foodUserMapper.toDto(foodUser);

        int databaseSizeBeforeCreate = foodUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFoodUsers() throws Exception {
        // Initialize the database
        foodUserRepository.saveAndFlush(foodUser);

        // Get all the foodUserList
        restFoodUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foodUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].eatenAt").value(hasItem(DEFAULT_EATEN_AT.toString())));
    }

    @Test
    @Transactional
    void getFoodUser() throws Exception {
        // Initialize the database
        foodUserRepository.saveAndFlush(foodUser);

        // Get the foodUser
        restFoodUserMockMvc
            .perform(get(ENTITY_API_URL_ID, foodUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foodUser.getId().intValue()))
            .andExpect(jsonPath("$.eatenAt").value(DEFAULT_EATEN_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFoodUser() throws Exception {
        // Get the foodUser
        restFoodUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFoodUser() throws Exception {
        // Initialize the database
        foodUserRepository.saveAndFlush(foodUser);

        int databaseSizeBeforeUpdate = foodUserRepository.findAll().size();

        // Update the foodUser
        FoodUser updatedFoodUser = foodUserRepository.findById(foodUser.getId()).get();
        // Disconnect from session so that the updates on updatedFoodUser are not directly saved in db
        em.detach(updatedFoodUser);
        updatedFoodUser.eatenAt(UPDATED_EATEN_AT);
        FoodUserDTO foodUserDTO = foodUserMapper.toDto(updatedFoodUser);

        restFoodUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeUpdate);
        FoodUser testFoodUser = foodUserList.get(foodUserList.size() - 1);
        assertThat(testFoodUser.getEatenAt()).isEqualTo(UPDATED_EATEN_AT);
    }

    @Test
    @Transactional
    void putNonExistingFoodUser() throws Exception {
        int databaseSizeBeforeUpdate = foodUserRepository.findAll().size();
        foodUser.setId(count.incrementAndGet());

        // Create the FoodUser
        FoodUserDTO foodUserDTO = foodUserMapper.toDto(foodUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFoodUser() throws Exception {
        int databaseSizeBeforeUpdate = foodUserRepository.findAll().size();
        foodUser.setId(count.incrementAndGet());

        // Create the FoodUser
        FoodUserDTO foodUserDTO = foodUserMapper.toDto(foodUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFoodUser() throws Exception {
        int databaseSizeBeforeUpdate = foodUserRepository.findAll().size();
        foodUser.setId(count.incrementAndGet());

        // Create the FoodUser
        FoodUserDTO foodUserDTO = foodUserMapper.toDto(foodUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFoodUserWithPatch() throws Exception {
        // Initialize the database
        foodUserRepository.saveAndFlush(foodUser);

        int databaseSizeBeforeUpdate = foodUserRepository.findAll().size();

        // Update the foodUser using partial update
        FoodUser partialUpdatedFoodUser = new FoodUser();
        partialUpdatedFoodUser.setId(foodUser.getId());

        restFoodUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoodUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoodUser))
            )
            .andExpect(status().isOk());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeUpdate);
        FoodUser testFoodUser = foodUserList.get(foodUserList.size() - 1);
        assertThat(testFoodUser.getEatenAt()).isEqualTo(DEFAULT_EATEN_AT);
    }

    @Test
    @Transactional
    void fullUpdateFoodUserWithPatch() throws Exception {
        // Initialize the database
        foodUserRepository.saveAndFlush(foodUser);

        int databaseSizeBeforeUpdate = foodUserRepository.findAll().size();

        // Update the foodUser using partial update
        FoodUser partialUpdatedFoodUser = new FoodUser();
        partialUpdatedFoodUser.setId(foodUser.getId());

        partialUpdatedFoodUser.eatenAt(UPDATED_EATEN_AT);

        restFoodUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoodUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoodUser))
            )
            .andExpect(status().isOk());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeUpdate);
        FoodUser testFoodUser = foodUserList.get(foodUserList.size() - 1);
        assertThat(testFoodUser.getEatenAt()).isEqualTo(UPDATED_EATEN_AT);
    }

    @Test
    @Transactional
    void patchNonExistingFoodUser() throws Exception {
        int databaseSizeBeforeUpdate = foodUserRepository.findAll().size();
        foodUser.setId(count.incrementAndGet());

        // Create the FoodUser
        FoodUserDTO foodUserDTO = foodUserMapper.toDto(foodUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, foodUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFoodUser() throws Exception {
        int databaseSizeBeforeUpdate = foodUserRepository.findAll().size();
        foodUser.setId(count.incrementAndGet());

        // Create the FoodUser
        FoodUserDTO foodUserDTO = foodUserMapper.toDto(foodUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFoodUser() throws Exception {
        int databaseSizeBeforeUpdate = foodUserRepository.findAll().size();
        foodUser.setId(count.incrementAndGet());

        // Create the FoodUser
        FoodUserDTO foodUserDTO = foodUserMapper.toDto(foodUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(foodUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FoodUser in the database
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFoodUser() throws Exception {
        // Initialize the database
        foodUserRepository.saveAndFlush(foodUser);

        int databaseSizeBeforeDelete = foodUserRepository.findAll().size();

        // Delete the foodUser
        restFoodUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, foodUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FoodUser> foodUserList = foodUserRepository.findAll();
        assertThat(foodUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
