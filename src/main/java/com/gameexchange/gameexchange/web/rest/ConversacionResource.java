package com.gameexchange.gameexchange.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gameexchange.gameexchange.domain.Conversacion;

import com.gameexchange.gameexchange.repository.ConversacionRepository;
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
 * REST controller for managing Conversacion.
 */
@RestController
@RequestMapping("/api")
public class ConversacionResource {

    private final Logger log = LoggerFactory.getLogger(ConversacionResource.class);
        
    @Inject
    private ConversacionRepository conversacionRepository;

    /**
     * POST  /conversacions : Create a new conversacion.
     *
     * @param conversacion the conversacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conversacion, or with status 400 (Bad Request) if the conversacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conversacions")
    @Timed
    public ResponseEntity<Conversacion> createConversacion(@RequestBody Conversacion conversacion) throws URISyntaxException {
        log.debug("REST request to save Conversacion : {}", conversacion);
        if (conversacion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("conversacion", "idexists", "A new conversacion cannot already have an ID")).body(null);
        }
        Conversacion result = conversacionRepository.save(conversacion);
        return ResponseEntity.created(new URI("/api/conversacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("conversacion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conversacions : Updates an existing conversacion.
     *
     * @param conversacion the conversacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conversacion,
     * or with status 400 (Bad Request) if the conversacion is not valid,
     * or with status 500 (Internal Server Error) if the conversacion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conversacions")
    @Timed
    public ResponseEntity<Conversacion> updateConversacion(@RequestBody Conversacion conversacion) throws URISyntaxException {
        log.debug("REST request to update Conversacion : {}", conversacion);
        if (conversacion.getId() == null) {
            return createConversacion(conversacion);
        }
        Conversacion result = conversacionRepository.save(conversacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("conversacion", conversacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conversacions : get all the conversacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of conversacions in body
     */
    @GetMapping("/conversacions")
    @Timed
    public List<Conversacion> getAllConversacions() {
        log.debug("REST request to get all Conversacions");
        List<Conversacion> conversacions = conversacionRepository.findAll();
        return conversacions;
    }

    /**
     * GET  /conversacions/:id : get the "id" conversacion.
     *
     * @param id the id of the conversacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conversacion, or with status 404 (Not Found)
     */
    @GetMapping("/conversacions/{id}")
    @Timed
    public ResponseEntity<Conversacion> getConversacion(@PathVariable Long id) {
        log.debug("REST request to get Conversacion : {}", id);
        Conversacion conversacion = conversacionRepository.findOne(id);
        return Optional.ofNullable(conversacion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /conversacions/:id : delete the "id" conversacion.
     *
     * @param id the id of the conversacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conversacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteConversacion(@PathVariable Long id) {
        log.debug("REST request to delete Conversacion : {}", id);
        conversacionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("conversacion", id.toString())).build();
    }

}
