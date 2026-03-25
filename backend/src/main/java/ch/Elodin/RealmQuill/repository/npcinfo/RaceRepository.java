package ch.Elodin.RealmQuill.repository.npcinfo;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RaceRepository extends JpaRepository<Race, Integer> {
    Optional<Race> findByRacename(String racename);
}
