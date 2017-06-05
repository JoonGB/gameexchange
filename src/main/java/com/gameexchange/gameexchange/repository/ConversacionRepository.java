package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.Conversacion;

import com.gameexchange.gameexchange.domain.UserExt;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Conversacion entity.
 */
@SuppressWarnings("unused")
public interface ConversacionRepository extends JpaRepository<Conversacion,Long> {

    @Query("select conversacion from Conversacion conversacion where conversacion.usuario1 = :userext or conversacion.usuario2 = :userext")
    List<Conversacion> findChatsByUser(@Param("userext") UserExt userExt);
}
