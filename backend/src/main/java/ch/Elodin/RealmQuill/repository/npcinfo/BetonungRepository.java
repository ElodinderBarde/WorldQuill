package ch.Elodin.RealmQuill.repository.npcinfo;

import ch.Elodin.RealmQuill.model.npcinfo.Betonung;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BetonungRepository extends JpaRepository<Betonung, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Betonung> findAll();

	Optional<Betonung> findBybetonung(String betonung);


}


