package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface HomePageRepository extends JpaRepository<Producto,Long> {

    List<Producto> findByNombreContaining(@Param("name") String name);




}
