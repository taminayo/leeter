package com.taminayo.leeter.authservice.repository;

import com.taminayo.leeter.authservice.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

    Optional<UserCredential> findByUsername(String username);
    Optional<UserCredential> findByEmail(String email);
}
