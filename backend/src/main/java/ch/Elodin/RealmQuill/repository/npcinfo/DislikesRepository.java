package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Dislikes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DislikesRepository extends JpaRepository<Dislikes, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Dislikes> findAll();

	Optional<Dislikes> findByDescription(String description);


}


