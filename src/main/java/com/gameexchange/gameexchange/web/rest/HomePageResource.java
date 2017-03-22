package com.gameexchange.gameexchange.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gameexchange.gameexchange.domain.Producto;
import com.gameexchange.gameexchange.repository.HomePageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomePageResource {

    private final Logger log = LoggerFactory.getLogger(HomePageResource.class);

    @Inject
    private HomePageRepository homePageRepository;

    @GetMapping("/home/{name}/like")
    @Timed
    public List<Producto> getProductosLike(@PathVariable String name) {
        log.debug("REST request to get all Productos like: {}", name);
        List<Producto> productos = homePageRepository.findByNombreContaining("%"+name+"%");
        return productos;
    }

}
