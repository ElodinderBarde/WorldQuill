package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Lastname;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastnameRepository extends JpaRepository<Lastname, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Lastname> findAll();

	Optional<Lastname> findByLastname(String lastname);
	 Optional<Lastname> findByLastnameAndRace(String lastname, Race race);


}


