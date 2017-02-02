package com.gameexchange.gameexchange.web.rest;

import com.gameexchange.gameexchange.GameexchangeApp;

import com.gameexchange.gameexchange.domain.Conversacion;
import com.gameexchange.gameexchange.repository.ConversacionRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.gameexchange.gameexchange.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConversacionResource REST controller.
 *
 * @see ConversacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameexchangeApp.class)
public class ConversacionResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Inject
    private ConversacionRepository conversacionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restConversacionMockMvc;

    private Conversacion conversacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConversacionResource conversacionResource = new ConversacionResource();
        ReflectionTestUtils.setField(conversacionResource, "conversacionRepository", conversacionRepository);
        this.restConversacionMockMvc = MockMvcBuilders.standaloneSetup(conversacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conversacion createEntity(EntityManager em) {
        Conversacion conversacion = new Conversacion()
                .creado(DEFAULT_CREADO);
        return conversacion;
    }

    @Before
    public void initTest() {
        conversacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createConversacion() throws Exception {
        int databaseSizeBeforeCreate = conversacionRepository.findAll().size();

        // Create the Conversacion

        restConversacionMockMvc.perform(post("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacion)))
            .andExpect(status().isCreated());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeCreate + 1);
        Conversacion testConversacion = conversacionList.get(conversacionList.size() - 1);
        assertThat(testConversacion.getCreado()).isEqualTo(DEFAULT_CREADO);
    }

    @Test
    @Transactional
    public void createConversacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conversacionRepository.findAll().size();

        // Create the Conversacion with an existing ID
        Conversacion existingConversacion = new Conversacion();
        existingConversacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConversacionMockMvc.perform(post("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingConversacion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConversacions() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        // Get all the conversacionList
        restConversacionMockMvc.perform(get("/api/conversacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conversacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].creado").value(hasItem(sameInstant(DEFAULT_CREADO))));
    }

    @Test
    @Transactional
    public void getConversacion() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);

        // Get the conversacion
        restConversacionMockMvc.perform(get("/api/conversacions/{id}", conversacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conversacion.getId().intValue()))
            .andExpect(jsonPath("$.creado").value(sameInstant(DEFAULT_CREADO)));
    }

    @Test
    @Transactional
    public void getNonExistingConversacion() throws Exception {
        // Get the conversacion
        restConversacionMockMvc.perform(get("/api/conversacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConversacion() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);
        int databaseSizeBeforeUpdate = conversacionRepository.findAll().size();

        // Update the conversacion
        Conversacion updatedConversacion = conversacionRepository.findOne(conversacion.getId());
        updatedConversacion
                .creado(UPDATED_CREADO);

        restConversacionMockMvc.perform(put("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConversacion)))
            .andExpect(status().isOk());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeUpdate);
        Conversacion testConversacion = conversacionList.get(conversacionList.size() - 1);
        assertThat(testConversacion.getCreado()).isEqualTo(UPDATED_CREADO);
    }

    @Test
    @Transactional
    public void updateNonExistingConversacion() throws Exception {
        int databaseSizeBeforeUpdate = conversacionRepository.findAll().size();

        // Create the Conversacion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConversacionMockMvc.perform(put("/api/conversacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conversacion)))
            .andExpect(status().isCreated());

        // Validate the Conversacion in the database
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConversacion() throws Exception {
        // Initialize the database
        conversacionRepository.saveAndFlush(conversacion);
        int databaseSizeBeforeDelete = conversacionRepository.findAll().size();

        // Get the conversacion
        restConversacionMockMvc.perform(delete("/api/conversacions/{id}", conversacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conversacion> conversacionList = conversacionRepository.findAll();
        assertThat(conversacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
