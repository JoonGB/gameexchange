package com.gameexchange.gameexchange.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gameexchange.gameexchange.domain.Foto;
import com.gameexchange.gameexchange.domain.Videojuego;

import com.gameexchange.gameexchange.repository.FotoRepository;
import com.gameexchange.gameexchange.repository.VideojuegoRepository;
import com.gameexchange.gameexchange.service.VideojuegoService;
import com.gameexchange.gameexchange.service.dto.IGDB_API.IGDBResponse;
import com.gameexchange.gameexchange.web.rest.util.HeaderUtil;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Videojuego.
 */
@RestController
@RequestMapping("/api")
public class VideojuegoResource {

    private final Logger log = LoggerFactory.getLogger(VideojuegoResource.class);

    @Inject
    private VideojuegoRepository videojuegoRepository;

    @Inject
    private VideojuegoService videojuegoService;


    @Inject
    private FotoRepository fotoRepository;

    /**
     * POST  /videojuegos : Create a new videojuego.
     *
     * @param videojuego the videojuego to create
     * @return the ResponseEntity with status 201 (Created) and with body the new videojuego, or with status 400 (Bad Request) if the videojuego has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/videojuegos")
    @Timed
    public ResponseEntity<Videojuego> createVideojuego(@RequestBody Videojuego videojuego) throws URISyntaxException {
        log.debug("REST request to save Videojuego : {}", videojuego);
        if (videojuego.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("videojuego", "idexists", "A new videojuego cannot already have an ID")).body(null);
        }

        Videojuego result = videojuegoRepository.save(videojuego);
        return ResponseEntity.created(new URI("/api/videojuegos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("videojuego", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /videojuegos : Updates an existing videojuego.
     *
     * @param videojuego the videojuego to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated videojuego,
     * or with status 400 (Bad Request) if the videojuego is not valid,
     * or with status 500 (Internal Server Error) if the videojuego couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/videojuegos")
    @Timed
    public ResponseEntity<Videojuego> updateVideojuego(@RequestBody Videojuego videojuego) throws URISyntaxException {
        log.debug("REST request to update Videojuego : {}", videojuego);
        if (videojuego.getId() == null) {
            return createVideojuego(videojuego);
        }
        Videojuego result = videojuegoRepository.save(videojuego);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("videojuego", videojuego.getId().toString()))
            .body(result);
    }

    /**
     * GET  /videojuegos : get all the videojuegos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of videojuegos in body
     */
    @GetMapping("/videojuegos")
    @Timed
    public List<Videojuego> getAllVideojuegos() {
        log.debug("REST request to get all Videojuegos");
        List<Videojuego> videojuegos = videojuegoRepository.findAllWithEagerRelationships();
        return videojuegos;
    }

    /**
     * GET  /videojuegos/:id : get the "id" videojuego.
     *
     * @param id the id of the videojuego to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the videojuego, or with status 404 (Not Found)
     */
    @GetMapping("/videojuegos/{id}")
    @Timed
    public ResponseEntity<Videojuego> getVideojuego(@PathVariable Long id) {
        log.debug("REST request to get Videojuego : {}", id);
        Videojuego videojuego = videojuegoRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(videojuego)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /videojuegos/:id : delete the "id" videojuego.
     *
     * @param id the id of the videojuego to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/videojuegos/{id}")
    @Timed
    public ResponseEntity<Void> deleteVideojuego(@PathVariable Long id) {
        log.debug("REST request to delete Videojuego : {}", id);
        videojuegoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("videojuego", id.toString())).build();
    }

    @GetMapping("/videojuegos/busqueda/{busqueda}")
    @Timed
    public List<IGDBResponse> busquedaVideojuego(@PathVariable String busqueda) throws UnirestException, IOException {
        List<IGDBResponse> resultados = videojuegoService.busquedaVideojuego(busqueda);
        return resultados;
    }

}
