package com.finemeet.authservice.entity;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_granted_authority")
public class UserGrantedAuthority implements GrantedAuthority {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userAuthorityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false)
    private Authority authority;

    public String getAuthority() {
        return authority.name();
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        UserGrantedAuthority that = (UserGrantedAuthority) o;
        return authority == that.authority;
    }

    @Override
    public int hashCode(){
        return Objects.hash(authority);
    }
}
