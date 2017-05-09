package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.Foto;
import com.gameexchange.gameexchange.domain.Producto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface HomePageRepository extends JpaRepository<Producto,Long> {

    // #1
    List<Producto> findByNombreContaining(@Param("name") String name);

    // #2
    List<Producto> findByPrecioBetween(@Param("pmin") Double pmin, @Param("pmax") Double pmax);

    // #3
    List<Producto> findByCreadoBetween(@Param("start") ZonedDateTime start, @Param("end") ZonedDateTime end);


}
