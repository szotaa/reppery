package pl.szotaa.repperybackend.supermemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.szotaa.repperybackend.common.entity.AbstractEntity;
import pl.szotaa.repperybackend.user.domain.User;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "groups")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Deck extends AbstractEntity implements Serializable {

    @NotNull
    @EqualsAndHashCode.Include
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @JsonIgnore
    @Builder.Default
    @JoinColumn(name = "deck_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flashcard> flashcards = new HashSet<>();

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
}
