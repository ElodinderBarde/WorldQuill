package ch.Elodin.RealmQuill.model.notes;

import ch.Elodin.RealmQuill.model.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worldnotes_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorldNotesCategory extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorldNotesFolder> folders = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (name == null || name.isBlank()) {
            name = "Allgemein";
        }
    }
}

