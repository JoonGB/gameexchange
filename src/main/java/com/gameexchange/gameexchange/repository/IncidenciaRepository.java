package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.Incidencia;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Incidencia entity.
 */
@SuppressWarnings("unused")
public interface IncidenciaRepository extends JpaRepository<Incidencia,Long> {

    @Query("select incidencia from Incidencia incidencia where incidencia.usuario.login = ?#{principal.username}")
    List<Incidencia> findByUsuarioIsCurrentUser();

}
