package com.finki.tilers.market.repositories;

import com.finki.tilers.market.model.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> getApplicationUserByEmail(String email);

    Optional<ApplicationUser> getApplicationUserByAccessToken(String accessToken);

}
