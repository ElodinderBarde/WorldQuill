package ch.Elodin.RealmQuill.repository.npcinfo;
import ch.Elodin.RealmQuill.model.npcinfo.Firstname;
import ch.Elodin.RealmQuill.model.npcinfo.Gender;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FirstnameRepository extends JpaRepository<Firstname, Integer> {
    Optional<Firstname> findByFirstnameAndRaceAndGender(String firstname, Race race, Gender gender);
    List<Firstname> findByRaceAndGender(Race race, Gender gender);
}
