package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.Categoria;
import com.gameexchange.gameexchange.domain.Producto;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Producto entity.
 */
@SuppressWarnings("unused")
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    @Query("select producto from Producto producto where producto.usuario.login = ?#{principal.username}")
    List<Producto> findByUsuarioIsCurrentUser();

    @Query("select producto from Producto producto where  :categoria member of producto.videojuego.categorias")
    List<Producto> findByCategoria(@Param ("categoria") Categoria categoria);










}
