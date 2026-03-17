package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.Npc;
import ch.Elodin.RealmQuill.model.npcinfo.Stats;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsRepository extends JpaRepository<Stats, Integer> {
	Optional<Stats> findByNpc(Npc npc);

}




