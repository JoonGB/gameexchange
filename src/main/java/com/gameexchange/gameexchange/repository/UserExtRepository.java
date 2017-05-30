package com.gameexchange.gameexchange.repository;

import com.gameexchange.gameexchange.domain.User;
import com.gameexchange.gameexchange.domain.UserExt;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UserExt entity.
 */
@SuppressWarnings("unused")
public interface UserExtRepository extends JpaRepository<UserExt,Long> {

    UserExt findByUser(@Param("name") User user);
}
