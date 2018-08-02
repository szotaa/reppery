package pl.szotaa.repperybackend.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.szotaa.repperybackend.common.entity.AbstractEntity;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class User extends AbstractEntity implements Serializable, UserDetails {

    @Email
    @NotEmpty
    @EqualsAndHashCode.Include
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @Builder.Default
    @Column(name = "is_enabled", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isEnabled = false;

    @NotNull
    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role = Role.ROLE_USER;

    @JsonIgnore
    @Builder.Default
    private String emailActivationToken = generateEmailActivationToken();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false; //account expiration not supported
    }

    @Override
    public boolean isAccountNonLocked() {
        return false; //account locking not supported
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false; //credentials expiration not supported
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    private static String generateEmailActivationToken(){
        return UUID.randomUUID().toString();
    }
}
