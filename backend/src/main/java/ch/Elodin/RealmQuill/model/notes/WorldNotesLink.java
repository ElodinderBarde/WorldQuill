
package ch.Elodin.RealmQuill.model.notes;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "worldnotes_note_link")
@Entity
public class WorldNotesLink extends BaseEntity {

    @Column(nullable = false)
    private String linkUrl;
    private String linkText;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "note_id", nullable = true)
    private WorldNotesNote note;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_note_id", nullable = true)
    private WorldNotesNote targetNote;



    public String getDescription() {
        if (linkText != null && !linkText.isEmpty()) {
            return linkText;
        } else {
            return linkUrl;
        }
    }
}

