package ch.Elodin.RealmQuill.repository.world;
import ch.Elodin.RealmQuill.model.QuestNpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestNpcRepository extends JpaRepository<QuestNpc, Integer> {
    List<QuestNpc> findByQuest_QuestID(int questID);

    @Query("SELECT qn FROM QuestNpc qn WHERE qn.npc.npc_ID = :npcId")
    List<QuestNpc> findByNpcId(@Param("npcId") int npcId);
}
