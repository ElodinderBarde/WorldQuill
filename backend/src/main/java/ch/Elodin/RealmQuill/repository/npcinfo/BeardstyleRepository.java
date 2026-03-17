package ch.Elodin.RealmQuill.repository.npcinfo;

import ch.Elodin.RealmQuill.model.npcinfo.Beardstyle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeardstyleRepository extends JpaRepository<Beardstyle, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Beardstyle> findAll();

	Optional<Beardstyle> findByname(String name);


}


