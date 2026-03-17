package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Firstname;
import ch.Elodin.RealmQuill.model.npcinfo.Gender;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirstnameRepository extends JpaRepository<Firstname, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Firstname> findAll();

	Optional<Firstname> findByFirstname(String Firstname);
	Optional<Firstname> findByFirstnameAndRaceAndGender(String firstname, Race race, Gender gender);


}


