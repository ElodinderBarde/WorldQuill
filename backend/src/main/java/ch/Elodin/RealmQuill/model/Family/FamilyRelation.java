package ch.Elodin.RealmQuill.model.Family;
import ch.Elodin.RealmQuill.model.Npc;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "npc_family_relation",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"source_npc_id", "related_npc_id", "relation_type"}))
public class FamilyRelation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_npc_id", nullable = false)
    private Npc sourceNpc;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "related_npc_id", nullable = false)
    private Npc relatedNpc;
    @Enumerated(EnumType.STRING)
    @Column(name = "relation_type", nullable = false)
    private RelationType relationType;
}
