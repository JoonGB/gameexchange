package com.gameexchange.gameexchange.web.rest;

import com.gameexchange.gameexchange.GameexchangeApp;

import com.gameexchange.gameexchange.domain.Incidencia;
import com.gameexchange.gameexchange.repository.IncidenciaRepository;

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
 * Test class for the IncidenciaResource REST controller.
 *
 * @see IncidenciaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameexchangeApp.class)
public class IncidenciaResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Inject
    private IncidenciaRepository incidenciaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restIncidenciaMockMvc;

    private Incidencia incidencia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IncidenciaResource incidenciaResource = new IncidenciaResource();
        ReflectionTestUtils.setField(incidenciaResource, "incidenciaRepository", incidenciaRepository);
        this.restIncidenciaMockMvc = MockMvcBuilders.standaloneSetup(incidenciaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incidencia createEntity(EntityManager em) {
        Incidencia incidencia = new Incidencia()
                .creado(DEFAULT_CREADO)
                .descripcion(DEFAULT_DESCRIPCION)
                .titulo(DEFAULT_TITULO);
        return incidencia;
    }

    @Before
    public void initTest() {
        incidencia = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncidencia() throws Exception {
        int databaseSizeBeforeCreate = incidenciaRepository.findAll().size();

        // Create the Incidencia

        restIncidenciaMockMvc.perform(post("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidencia)))
            .andExpect(status().isCreated());

        // Validate the Incidencia in the database
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeCreate + 1);
        Incidencia testIncidencia = incidenciaList.get(incidenciaList.size() - 1);
        assertThat(testIncidencia.getCreado()).isEqualTo(DEFAULT_CREADO);
        assertThat(testIncidencia.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testIncidencia.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createIncidenciaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidenciaRepository.findAll().size();

        // Create the Incidencia with an existing ID
        Incidencia existingIncidencia = new Incidencia();
        existingIncidencia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidenciaMockMvc.perform(post("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingIncidencia)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIncidencias() throws Exception {
        // Initialize the database
        incidenciaRepository.saveAndFlush(incidencia);

        // Get all the incidenciaList
        restIncidenciaMockMvc.perform(get("/api/incidencias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].creado").value(hasItem(sameInstant(DEFAULT_CREADO))))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())));
    }

    @Test
    @Transactional
    public void getIncidencia() throws Exception {
        // Initialize the database
        incidenciaRepository.saveAndFlush(incidencia);

        // Get the incidencia
        restIncidenciaMockMvc.perform(get("/api/incidencias/{id}", incidencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incidencia.getId().intValue()))
            .andExpect(jsonPath("$.creado").value(sameInstant(DEFAULT_CREADO)))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIncidencia() throws Exception {
        // Get the incidencia
        restIncidenciaMockMvc.perform(get("/api/incidencias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncidencia() throws Exception {
        // Initialize the database
        incidenciaRepository.saveAndFlush(incidencia);
        int databaseSizeBeforeUpdate = incidenciaRepository.findAll().size();

        // Update the incidencia
        Incidencia updatedIncidencia = incidenciaRepository.findOne(incidencia.getId());
        updatedIncidencia
                .creado(UPDATED_CREADO)
                .descripcion(UPDATED_DESCRIPCION)
                .titulo(UPDATED_TITULO);

        restIncidenciaMockMvc.perform(put("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIncidencia)))
            .andExpect(status().isOk());

        // Validate the Incidencia in the database
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeUpdate);
        Incidencia testIncidencia = incidenciaList.get(incidenciaList.size() - 1);
        assertThat(testIncidencia.getCreado()).isEqualTo(UPDATED_CREADO);
        assertThat(testIncidencia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testIncidencia.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingIncidencia() throws Exception {
        int databaseSizeBeforeUpdate = incidenciaRepository.findAll().size();

        // Create the Incidencia

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIncidenciaMockMvc.perform(put("/api/incidencias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incidencia)))
            .andExpect(status().isCreated());

        // Validate the Incidencia in the database
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIncidencia() throws Exception {
        // Initialize the database
        incidenciaRepository.saveAndFlush(incidencia);
        int databaseSizeBeforeDelete = incidenciaRepository.findAll().size();

        // Get the incidencia
        restIncidenciaMockMvc.perform(delete("/api/incidencias/{id}", incidencia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Incidencia> incidenciaList = incidenciaRepository.findAll();
        assertThat(incidenciaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
