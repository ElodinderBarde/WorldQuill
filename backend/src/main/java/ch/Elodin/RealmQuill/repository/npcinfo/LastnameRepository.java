package ch.Elodin.RealmQuill.repository.npcinfo;
import ch.Elodin.RealmQuill.model.npcinfo.Lastname;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LastnameRepository extends JpaRepository<Lastname, Integer> {
    Optional<Lastname> findByLastnameAndRace(String lastname, Race race);
    List<Lastname> findByRace(Race race);
}
