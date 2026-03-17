package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Jackets;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JacketsRepository extends JpaRepository<Jackets, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Jackets> findAll();

	Optional<Jackets> findByname(String name);


}


