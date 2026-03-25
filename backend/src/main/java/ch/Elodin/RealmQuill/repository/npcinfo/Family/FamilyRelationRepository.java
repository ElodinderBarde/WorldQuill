package ch.Elodin.RealmQuill.repository.npcinfo.Family;
import ch.Elodin.RealmQuill.model.Family.FamilyRelation;
import ch.Elodin.RealmQuill.model.Family.RelationType;
import ch.Elodin.RealmQuill.model.Npc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FamilyRelationRepository extends JpaRepository<FamilyRelation, Long> {
    List<FamilyRelation> findBySourceNpc(Npc sourceNpc);
    boolean existsBySourceNpcAndRelatedNpcAndRelationType(
            Npc sourceNpc, Npc relatedNpc, RelationType relationType);
}
