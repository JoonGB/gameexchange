package com.gameexchange.gameexchange.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gameexchange.gameexchange.domain.Categoria;
import com.gameexchange.gameexchange.domain.Foto;
import com.gameexchange.gameexchange.domain.Producto;

import com.gameexchange.gameexchange.repository.CategoriaRepository;
import com.gameexchange.gameexchange.repository.ProductoRepository;
import com.gameexchange.gameexchange.service.ProductService;
import com.gameexchange.gameexchange.service.dto.ProductoDTO;
import com.gameexchange.gameexchange.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Producto. q
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

    @Inject
    private ProductoRepository productoRepository;
    @Inject
    private CategoriaRepository categoriaRepository;

    @Inject
    private ProductService productService;

    /**
     * POST  /productos : Create a new producto.
     *
     * @param producto the producto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new producto, or with status 400 (Bad Request) if the producto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/productos")
    @Timed
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", producto);
        if (producto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("producto", "idexists", "A new producto cannot already have an ID")).body(null);
        }
        Producto result = productoRepository.save(producto);
        return ResponseEntity.created(new URI("/api/productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("producto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productos : Updates an existing producto.
     *
     * @param producto the producto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated producto,
     * or with status 400 (Bad Request) if the producto is not valid,
     * or with status 500 (Internal Server Error) if the producto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/productos")
    @Timed
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", producto);
        if (producto.getId() == null) {
            return createProducto(producto);
        }
        Producto result = productoRepository.save(producto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("producto", producto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productos : get all the productos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productos in body
     */
    @GetMapping("/productos")
    @Timed
    public List<Producto> getAllProductos() {
        log.debug("REST request to get all Productos");
        List<Producto> productos = productoRepository.findAll();
        return productos;
    }

    @GetMapping("/productosdto")
    @Timed
    public List<ProductoDTO> getAllProductosDTO() {
        log.debug("REST request to get all Productos");
        List<ProductoDTO> productos = productService.getProductos();
        return productos;
    }

    /**
     * GET  /productos/:id : get the "id" producto.
     *
     * @param id the id of the producto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the producto, or with status 404 (Not Found)
     */
    @GetMapping("/productos/{id}")
    @Timed
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        Producto producto = productoRepository.findOne(id);
        return Optional.ofNullable(producto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/productosdto/{id}")
    @Timed
    @Transactional
    public ResponseEntity<ProductoDTO> getProductoDTO(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        Producto producto = productoRepository.findOne(id);

        return Optional.ofNullable(producto)
            .map(producto1 -> productService.getProductoDTO(producto1))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /productos/:id : get the "id" producto.
     *
     * @param id the id of the producto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the producto, or with status 404 (Not Found)
     */
    @GetMapping("/productos/{id}/fotos")
    @Timed
    public ResponseEntity<Set<Foto>> getFotosDeProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);

        return new ResponseEntity<>(productService.getFotos(id), HttpStatus.OK);

    }

    @GetMapping("/productos/{id}/fotoPrincipal")
    @Timed
    public ResponseEntity<Foto> getFotoPrincipalDeProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);

        Optional<Foto> fotoOptional = productService.getFotoPrincipal(id);

        if (fotoOptional.isPresent()) {
            return new ResponseEntity<>(fotoOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    /**
     * DELETE  /productos/:id : delete the "id" producto.
     *
     * @param id the id of the producto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/productos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        productoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("producto", id.toString())).build();
    }

    @GetMapping("/productos/byCategoria/{nombre}")
    @Timed
    public List<Producto> getProductoByCategoria(@PathVariable String nombre) {
        log.debug("REST request to get Producto by categoria : {}", nombre);

        List<Producto> productos = new ArrayList<>();

        Optional<Categoria> categoriaOptional = categoriaRepository.findByNombre(nombre);


        if (categoriaOptional.isPresent()) {
            productos = productoRepository.findByCategoria(categoriaOptional.get());
        }

        return productos;
    }


}
