package com.gameexchange.gameexchange.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gameexchange.gameexchange.domain.Venta;

import com.gameexchange.gameexchange.repository.VentaRepository;
import com.gameexchange.gameexchange.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Venta.
 */
@RestController
@RequestMapping("/api")
public class VentaResource {

    private final Logger log = LoggerFactory.getLogger(VentaResource.class);
        
    @Inject
    private VentaRepository ventaRepository;

    /**
     * POST  /ventas : Create a new venta.
     *
     * @param venta the venta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new venta, or with status 400 (Bad Request) if the venta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ventas")
    @Timed
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta) throws URISyntaxException {
        log.debug("REST request to save Venta : {}", venta);
        if (venta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("venta", "idexists", "A new venta cannot already have an ID")).body(null);
        }
        Venta result = ventaRepository.save(venta);
        return ResponseEntity.created(new URI("/api/ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("venta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ventas : Updates an existing venta.
     *
     * @param venta the venta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated venta,
     * or with status 400 (Bad Request) if the venta is not valid,
     * or with status 500 (Internal Server Error) if the venta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ventas")
    @Timed
    public ResponseEntity<Venta> updateVenta(@RequestBody Venta venta) throws URISyntaxException {
        log.debug("REST request to update Venta : {}", venta);
        if (venta.getId() == null) {
            return createVenta(venta);
        }
        Venta result = ventaRepository.save(venta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("venta", venta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ventas : get all the ventas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ventas in body
     */
    @GetMapping("/ventas")
    @Timed
    public List<Venta> getAllVentas() {
        log.debug("REST request to get all Ventas");
        List<Venta> ventas = ventaRepository.findAll();
        return ventas;
    }

    /**
     * GET  /ventas/:id : get the "id" venta.
     *
     * @param id the id of the venta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the venta, or with status 404 (Not Found)
     */
    @GetMapping("/ventas/{id}")
    @Timed
    public ResponseEntity<Venta> getVenta(@PathVariable Long id) {
        log.debug("REST request to get Venta : {}", id);
        Venta venta = ventaRepository.findOne(id);
        return Optional.ofNullable(venta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ventas/:id : delete the "id" venta.
     *
     * @param id the id of the venta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ventas/{id}")
    @Timed
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        log.debug("REST request to delete Venta : {}", id);
        ventaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("venta", id.toString())).build();
    }

}
