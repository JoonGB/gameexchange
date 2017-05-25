package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.Videojuego;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Videojuego entity.
 */
@SuppressWarnings("unused")
public interface VideojuegoRepository extends JpaRepository<Videojuego,Long> {

    @Query("select distinct videojuego from Videojuego videojuego left join fetch videojuego.categorias")
    List<Videojuego> findAllWithEagerRelationships();

    @Query("select videojuego from Videojuego videojuego left join fetch videojuego.categorias where videojuego.id =:id")
    Videojuego findOneWithEagerRelationships(@Param("id") Long id);


}
