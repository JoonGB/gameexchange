package com.gameexchange.gameexchange.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gameexchange.gameexchange.domain.Incidencia;

import com.gameexchange.gameexchange.repository.IncidenciaRepository;
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
 * REST controller for managing Incidencia.
 */
@RestController
@RequestMapping("/api")
public class IncidenciaResource {

    private final Logger log = LoggerFactory.getLogger(IncidenciaResource.class);
        
    @Inject
    private IncidenciaRepository incidenciaRepository;

    /**
     * POST  /incidencias : Create a new incidencia.
     *
     * @param incidencia the incidencia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incidencia, or with status 400 (Bad Request) if the incidencia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/incidencias")
    @Timed
    public ResponseEntity<Incidencia> createIncidencia(@RequestBody Incidencia incidencia) throws URISyntaxException {
        log.debug("REST request to save Incidencia : {}", incidencia);
        if (incidencia.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("incidencia", "idexists", "A new incidencia cannot already have an ID")).body(null);
        }
        Incidencia result = incidenciaRepository.save(incidencia);
        return ResponseEntity.created(new URI("/api/incidencias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("incidencia", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /incidencias : Updates an existing incidencia.
     *
     * @param incidencia the incidencia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incidencia,
     * or with status 400 (Bad Request) if the incidencia is not valid,
     * or with status 500 (Internal Server Error) if the incidencia couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incidencias")
    @Timed
    public ResponseEntity<Incidencia> updateIncidencia(@RequestBody Incidencia incidencia) throws URISyntaxException {
        log.debug("REST request to update Incidencia : {}", incidencia);
        if (incidencia.getId() == null) {
            return createIncidencia(incidencia);
        }
        Incidencia result = incidenciaRepository.save(incidencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("incidencia", incidencia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incidencias : get all the incidencias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of incidencias in body
     */
    @GetMapping("/incidencias")
    @Timed
    public List<Incidencia> getAllIncidencias() {
        log.debug("REST request to get all Incidencias");
        List<Incidencia> incidencias = incidenciaRepository.findAll();
        return incidencias;
    }

    /**
     * GET  /incidencias/:id : get the "id" incidencia.
     *
     * @param id the id of the incidencia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incidencia, or with status 404 (Not Found)
     */
    @GetMapping("/incidencias/{id}")
    @Timed
    public ResponseEntity<Incidencia> getIncidencia(@PathVariable Long id) {
        log.debug("REST request to get Incidencia : {}", id);
        Incidencia incidencia = incidenciaRepository.findOne(id);
        return Optional.ofNullable(incidencia)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /incidencias/:id : delete the "id" incidencia.
     *
     * @param id the id of the incidencia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incidencias/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncidencia(@PathVariable Long id) {
        log.debug("REST request to delete Incidencia : {}", id);
        incidenciaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("incidencia", id.toString())).build();
    }

}
