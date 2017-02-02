package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.Venta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Venta entity.
 */
@SuppressWarnings("unused")
public interface VentaRepository extends JpaRepository<Venta,Long> {

    @Query("select venta from Venta venta where venta.vendedor.login = ?#{principal.username}")
    List<Venta> findByVendedorIsCurrentUser();

    @Query("select venta from Venta venta where venta.cliente.login = ?#{principal.username}")
    List<Venta> findByClienteIsCurrentUser();

}
