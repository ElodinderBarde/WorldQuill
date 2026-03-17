package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Ideals;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdealsRepository extends JpaRepository<Ideals, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Ideals> findAll();

	Optional<Ideals> findByDescription(String description);


}


