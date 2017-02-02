package com.gameexchange.gameexchange.web.rest;

import com.gameexchange.gameexchange.GameexchangeApp;

import com.gameexchange.gameexchange.domain.Videojuego;
import com.gameexchange.gameexchange.repository.VideojuegoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VideojuegoResource REST controller.
 *
 * @see VideojuegoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameexchangeApp.class)
public class VideojuegoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Inject
    private VideojuegoRepository videojuegoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVideojuegoMockMvc;

    private Videojuego videojuego;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VideojuegoResource videojuegoResource = new VideojuegoResource();
        ReflectionTestUtils.setField(videojuegoResource, "videojuegoRepository", videojuegoRepository);
        this.restVideojuegoMockMvc = MockMvcBuilders.standaloneSetup(videojuegoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Videojuego createEntity(EntityManager em) {
        Videojuego videojuego = new Videojuego()
                .nombre(DEFAULT_NOMBRE);
        return videojuego;
    }

    @Before
    public void initTest() {
        videojuego = createEntity(em);
    }

    @Test
    @Transactional
    public void createVideojuego() throws Exception {
        int databaseSizeBeforeCreate = videojuegoRepository.findAll().size();

        // Create the Videojuego

        restVideojuegoMockMvc.perform(post("/api/videojuegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videojuego)))
            .andExpect(status().isCreated());

        // Validate the Videojuego in the database
        List<Videojuego> videojuegoList = videojuegoRepository.findAll();
        assertThat(videojuegoList).hasSize(databaseSizeBeforeCreate + 1);
        Videojuego testVideojuego = videojuegoList.get(videojuegoList.size() - 1);
        assertThat(testVideojuego.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createVideojuegoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = videojuegoRepository.findAll().size();

        // Create the Videojuego with an existing ID
        Videojuego existingVideojuego = new Videojuego();
        existingVideojuego.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVideojuegoMockMvc.perform(post("/api/videojuegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingVideojuego)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Videojuego> videojuegoList = videojuegoRepository.findAll();
        assertThat(videojuegoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVideojuegos() throws Exception {
        // Initialize the database
        videojuegoRepository.saveAndFlush(videojuego);

        // Get all the videojuegoList
        restVideojuegoMockMvc.perform(get("/api/videojuegos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(videojuego.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getVideojuego() throws Exception {
        // Initialize the database
        videojuegoRepository.saveAndFlush(videojuego);

        // Get the videojuego
        restVideojuegoMockMvc.perform(get("/api/videojuegos/{id}", videojuego.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(videojuego.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVideojuego() throws Exception {
        // Get the videojuego
        restVideojuegoMockMvc.perform(get("/api/videojuegos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideojuego() throws Exception {
        // Initialize the database
        videojuegoRepository.saveAndFlush(videojuego);
        int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();

        // Update the videojuego
        Videojuego updatedVideojuego = videojuegoRepository.findOne(videojuego.getId());
        updatedVideojuego
                .nombre(UPDATED_NOMBRE);

        restVideojuegoMockMvc.perform(put("/api/videojuegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVideojuego)))
            .andExpect(status().isOk());

        // Validate the Videojuego in the database
        List<Videojuego> videojuegoList = videojuegoRepository.findAll();
        assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
        Videojuego testVideojuego = videojuegoList.get(videojuegoList.size() - 1);
        assertThat(testVideojuego.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingVideojuego() throws Exception {
        int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();

        // Create the Videojuego

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVideojuegoMockMvc.perform(put("/api/videojuegos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(videojuego)))
            .andExpect(status().isCreated());

        // Validate the Videojuego in the database
        List<Videojuego> videojuegoList = videojuegoRepository.findAll();
        assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVideojuego() throws Exception {
        // Initialize the database
        videojuegoRepository.saveAndFlush(videojuego);
        int databaseSizeBeforeDelete = videojuegoRepository.findAll().size();

        // Get the videojuego
        restVideojuegoMockMvc.perform(delete("/api/videojuegos/{id}", videojuego.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Videojuego> videojuegoList = videojuegoRepository.findAll();
        assertThat(videojuegoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
