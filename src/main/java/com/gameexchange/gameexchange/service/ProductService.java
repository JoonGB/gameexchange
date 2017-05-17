package com.gameexchange.gameexchange.service;

import com.gameexchange.gameexchange.domain.Foto;
import com.gameexchange.gameexchange.domain.Producto;
import com.gameexchange.gameexchange.repository.FotoRepository;
import com.gameexchange.gameexchange.repository.ProductoRepository;
import com.gameexchange.gameexchange.service.dto.ProductoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by DAM on 2/5/17.
 */
@Service
@Transactional
public class ProductService {
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Inject
    private ProductoRepository productoRepository;

    @Inject
    private FotoRepository fotoRepository;

    public Optional<Foto> getFotoPrincipal(Long idProducto) {

        Producto producto =  productoRepository.findOne(idProducto);

        if (producto == null) {
            return Optional.empty();
        }


        return  producto.getFotos().stream().findFirst();


    }

    public Set<Foto> getFotos(Long idProducto) {

        Producto producto =  productoRepository.findOne(idProducto);

        if (producto == null) {
            return null;
        }

        return producto.getFotos();

    }

    public List<ProductoDTO> getProductos() {


        return productoRepository.findAll().stream().map(producto ->

            getProductoDTO(producto)


        ).collect(Collectors.toList());

    }

    public ProductoDTO getProductoDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();

        productoDTO.setId(producto.getId());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setCreado(producto.getCreado());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setUsuario(producto.getUsuario());
        productoDTO.setFotos(new ArrayList<>(producto.getFotos()));
        productoDTO.setVentas(new ArrayList<>(producto.getVentas()));
        productoDTO.setVideojuego(producto.getVideojuego());

        productoDTO.setFotoPrincipal(getFotoPrincipal(producto.getId()).get());

        return  productoDTO;
    }



}
