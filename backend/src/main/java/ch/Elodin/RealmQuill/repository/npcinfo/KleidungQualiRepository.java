package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.KleidungQuali;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KleidungQualiRepository extends JpaRepository<KleidungQuali, Integer> {
        @SuppressWarnings("null")
				@Override
	List<KleidungQuali> findAll();

	Optional<KleidungQuali> findByDescription(String description);


}


