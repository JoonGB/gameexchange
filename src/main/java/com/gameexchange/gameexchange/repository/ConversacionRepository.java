package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.Conversacion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Conversacion entity.
 */
@SuppressWarnings("unused")
public interface ConversacionRepository extends JpaRepository<Conversacion,Long> {

}
