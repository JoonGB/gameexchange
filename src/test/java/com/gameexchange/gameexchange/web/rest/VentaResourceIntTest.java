package com.gameexchange.gameexchange.web.rest;

import com.gameexchange.gameexchange.GameexchangeApp;

import com.gameexchange.gameexchange.domain.Venta;
import com.gameexchange.gameexchange.repository.VentaRepository;

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
 * Test class for the VentaResource REST controller.
 *
 * @see VentaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameexchangeApp.class)
public class VentaResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREADO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREADO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_VALORACION_CLIENTE = 1;
    private static final Integer UPDATED_VALORACION_CLIENTE = 2;

    private static final Integer DEFAULT_VALOREACION_VENDEDOR = 1;
    private static final Integer UPDATED_VALOREACION_VENDEDOR = 2;

    private static final String DEFAULT_COMENTARIO_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_COMENTARIO_VENDEDOR = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO_VENDEDOR = "BBBBBBBBBB";

    @Inject
    private VentaRepository ventaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVentaMockMvc;

    private Venta venta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VentaResource ventaResource = new VentaResource();
        ReflectionTestUtils.setField(ventaResource, "ventaRepository", ventaRepository);
        this.restVentaMockMvc = MockMvcBuilders.standaloneSetup(ventaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venta createEntity(EntityManager em) {
        Venta venta = new Venta()
                .creado(DEFAULT_CREADO)
                .valoracionCliente(DEFAULT_VALORACION_CLIENTE)
                .valoreacionVendedor(DEFAULT_VALOREACION_VENDEDOR)
                .comentarioCliente(DEFAULT_COMENTARIO_CLIENTE)
                .comentarioVendedor(DEFAULT_COMENTARIO_VENDEDOR);
        return venta;
    }

    @Before
    public void initTest() {
        venta = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenta() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();

        // Create the Venta

        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venta)))
            .andExpect(status().isCreated());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate + 1);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getCreado()).isEqualTo(DEFAULT_CREADO);
        assertThat(testVenta.getValoracionCliente()).isEqualTo(DEFAULT_VALORACION_CLIENTE);
        assertThat(testVenta.getValoreacionVendedor()).isEqualTo(DEFAULT_VALOREACION_VENDEDOR);
        assertThat(testVenta.getComentarioCliente()).isEqualTo(DEFAULT_COMENTARIO_CLIENTE);
        assertThat(testVenta.getComentarioVendedor()).isEqualTo(DEFAULT_COMENTARIO_VENDEDOR);
    }

    @Test
    @Transactional
    public void createVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();

        // Create the Venta with an existing ID
        Venta existingVenta = new Venta();
        existingVenta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingVenta)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVentas() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get all the ventaList
        restVentaMockMvc.perform(get("/api/ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venta.getId().intValue())))
            .andExpect(jsonPath("$.[*].creado").value(hasItem(sameInstant(DEFAULT_CREADO))))
            .andExpect(jsonPath("$.[*].valoracionCliente").value(hasItem(DEFAULT_VALORACION_CLIENTE)))
            .andExpect(jsonPath("$.[*].valoreacionVendedor").value(hasItem(DEFAULT_VALOREACION_VENDEDOR)))
            .andExpect(jsonPath("$.[*].comentarioCliente").value(hasItem(DEFAULT_COMENTARIO_CLIENTE.toString())))
            .andExpect(jsonPath("$.[*].comentarioVendedor").value(hasItem(DEFAULT_COMENTARIO_VENDEDOR.toString())));
    }

    @Test
    @Transactional
    public void getVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", venta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venta.getId().intValue()))
            .andExpect(jsonPath("$.creado").value(sameInstant(DEFAULT_CREADO)))
            .andExpect(jsonPath("$.valoracionCliente").value(DEFAULT_VALORACION_CLIENTE))
            .andExpect(jsonPath("$.valoreacionVendedor").value(DEFAULT_VALOREACION_VENDEDOR))
            .andExpect(jsonPath("$.comentarioCliente").value(DEFAULT_COMENTARIO_CLIENTE.toString()))
            .andExpect(jsonPath("$.comentarioVendedor").value(DEFAULT_COMENTARIO_VENDEDOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVenta() throws Exception {
        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);
        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Update the venta
        Venta updatedVenta = ventaRepository.findOne(venta.getId());
        updatedVenta
                .creado(UPDATED_CREADO)
                .valoracionCliente(UPDATED_VALORACION_CLIENTE)
                .valoreacionVendedor(UPDATED_VALOREACION_VENDEDOR)
                .comentarioCliente(UPDATED_COMENTARIO_CLIENTE)
                .comentarioVendedor(UPDATED_COMENTARIO_VENDEDOR);

        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVenta)))
            .andExpect(status().isOk());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getCreado()).isEqualTo(UPDATED_CREADO);
        assertThat(testVenta.getValoracionCliente()).isEqualTo(UPDATED_VALORACION_CLIENTE);
        assertThat(testVenta.getValoreacionVendedor()).isEqualTo(UPDATED_VALOREACION_VENDEDOR);
        assertThat(testVenta.getComentarioCliente()).isEqualTo(UPDATED_COMENTARIO_CLIENTE);
        assertThat(testVenta.getComentarioVendedor()).isEqualTo(UPDATED_COMENTARIO_VENDEDOR);
    }

    @Test
    @Transactional
    public void updateNonExistingVenta() throws Exception {
        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Create the Venta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(venta)))
            .andExpect(status().isCreated());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);
        int databaseSizeBeforeDelete = ventaRepository.findAll().size();

        // Get the venta
        restVentaMockMvc.perform(delete("/api/ventas/{id}", venta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
