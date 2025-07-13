package com.finemeet.authservice.repository;

import com.finemeet.authservice.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {

    /**
     * Finds a user entity by its email.
     *
     * @param email The email of the user.
     * @return An optional containing the user entity if found.
     */
    Optional<AuthUser> findByEmail(String email);

    /**
     * Updates the locked status of a user based on the given email.
     *
     * @param email         The email of the user.
     * @param accountLocked The new locked status to set.
     */
    @Modifying
    @Query("UPDATE AuthUser u SET u.accountNonLocked = :accountLocked WHERE u.email = :email")
    void setAccountLockedStatus(@Param("email") String email, @Param("accountLocked") boolean accountLocked);

}
