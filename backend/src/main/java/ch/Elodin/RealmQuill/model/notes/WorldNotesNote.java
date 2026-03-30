package ch.Elodin.RealmQuill.model.notes;

import ch.Elodin.RealmQuill.model.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worldnotes_note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorldNotesNote extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content = "Hier könnte Ihre Not";

    // User
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "folder_id", nullable = true)
    private WorldNotesFolder folder;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = true)
    private WorldNotesCategory category;

    // Links in the note
    @JsonIgnore
    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorldNotesLink> links = new ArrayList<>();



}
