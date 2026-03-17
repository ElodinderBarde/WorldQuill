package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Flaw;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlawRepository extends JpaRepository<Flaw, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Flaw> findAll();

	Optional<Flaw> findByDescription(String description);


}


