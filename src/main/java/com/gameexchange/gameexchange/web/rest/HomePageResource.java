package com.gameexchange.gameexchange.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gameexchange.gameexchange.domain.Foto;
import com.gameexchange.gameexchange.domain.Producto;
import com.gameexchange.gameexchange.repository.HomePageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HomePageResource {

    private final Logger log = LoggerFactory.getLogger(HomePageResource.class);

    @Inject
    private HomePageRepository homePageRepository;

    // #1 Función: Buscador
    @GetMapping("/home/productos/like/{name}")
    @Timed
    public List<Producto> getProductosLike(@PathVariable String name) {
        log.debug("REST request to get all Productos like: {}", name);
        List<Producto> productos = homePageRepository.findByNombreContaining("%"+name+"%");
        return productos;
    }

    // #2 Función: Filtrar por precio
    @GetMapping("/home/productos/precio/between/{pmin}/{pmax}")
    @Timed
    public List<Producto> getProductosPrecioBetween(@PathVariable Double pmin, Double pmax) {
        log.debug("REST request to get all Productos with Precio between: {}", pmin, pmax);
        List<Producto> productos = homePageRepository.findByPrecioBetween(pmin, pmax);
        return productos;
    }

    // #3 Función: Filtrar por fecha
    @GetMapping("/home/productos/creado/between/{fmax}")
    @Timed
    public List<Producto> getProductosCreadoAfter(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy")LocalDate fmax) {
        log.debug("REST request to get all Productos with Creado after: {}", fmax);

        ZonedDateTime start = ZonedDateTime.of(fmax, LocalTime.MIN, ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(LocalDate.now(),LocalTime.MAX, ZoneId.systemDefault());

        List<Producto> productos = homePageRepository.findByCreadoBetween(start,end);

        return  productos;
    }

}
