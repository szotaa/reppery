package pl.szotaa.repperybackend.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.szotaa.repperybackend.common.entity.AbstractEntity;
import pl.szotaa.repperybackend.supermemo.domain.Group;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity implements Serializable, UserDetails {

    @Email
    @NotEmpty
    @EqualsAndHashCode.Include
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty
    @ToString.Exclude
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
    @Column(name = "role", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role = Role.ROLE_USER;

    @JsonIgnore
    @Builder.Default
    @ToString.Exclude
    @Column(name = "email_activation_token", unique = true)
    private String emailActivationToken = generateEmailActivationToken();

    @NotNull
    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private Set<Group> groups = new HashSet<>();

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
        return true; //account expiration not supported
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //account locking not supported
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //credentials expiration not supported
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @PrePersist
    public void prePersist(){
        this.email = this.email.toLowerCase();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this){
            return true;
        }
        if(!(o instanceof User)){
            return false;
        }
        User user = (User) o;
        if(!(user.getEmail().toLowerCase().equals(this.email.toLowerCase()))){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = this.email.toLowerCase().hashCode();
        return result * 31;
    }

    private static String generateEmailActivationToken(){
        return UUID.randomUUID().toString();
    }
}
