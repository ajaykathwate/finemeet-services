package com.finemeet.authservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "auth_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_email_verified", nullable = false)
    private boolean isEmailVerified = false;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked = true;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<UserGrantedAuthority> authorities = new ArrayList<>();

    /*
     * Helper methods to add/remove authorities
     */
    public void addAuthority(UserGrantedAuthority authority) {
        authority.setUser(this);
        authorities.add(authority);
    }

    public void removeAuthority(UserGrantedAuthority authority) {
        authorities.remove(authority);
        authority.setUser(null);
    }

    /*
     * Override equals to define logical equality:
     * Two AuthUser objects are considered equal if their 'id' fields are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AuthUser user = (AuthUser) o;
        return new EqualsBuilder()
                .append(id, user.id)
                .isEquals();
    }

    /*
     * Override hashCode to ensure consistent hashing based on 'id' field,
     * which is important for using User in hash-based collections like HashSet or HashMap.
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    /*
     * Override toString for readable object representation,
     * useful for logging, debugging, and console output.
     */
    @Override
    public String toString() {
        return "AuthUser{" +
                "userId='" + id + '\'' +
                '}';
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}