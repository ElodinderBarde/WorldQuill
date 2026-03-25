package ch.Elodin.RealmQuill.repository.npcinfo;
import ch.Elodin.RealmQuill.model.npcinfo.Personality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalityRepository extends JpaRepository<Personality, Integer> {
}
