package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.Conversacion;
import com.gameexchange.gameexchange.domain.Mensaje;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Mensaje entity.
 */
@SuppressWarnings("unused")
public interface MensajeRepository extends JpaRepository<Mensaje,Long> {

    @Query("select mensaje from Mensaje mensaje where mensaje.emisor.login = ?#{principal.username}")
    List<Mensaje> findByEmisorIsCurrentUser();

    @Query("select mensaje from Mensaje mensaje where mensaje.receptor.login = ?#{principal.username}")
    List<Mensaje> findByReceptorIsCurrentUser();

    @Query("select mensaje from Mensaje mensaje where mensaje.conversacion = :conversacion")
    List<Mensaje> findByConversacion(@Param("conversacion") Conversacion conversacion);

}
