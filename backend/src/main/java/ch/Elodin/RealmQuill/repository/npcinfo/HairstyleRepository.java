package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Hairstyle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HairstyleRepository extends JpaRepository<Hairstyle, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Hairstyle> findAll();

	Optional<Hairstyle> findByname(String name);


}


