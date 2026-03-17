package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Haircolor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HaircolorRepository extends JpaRepository<Haircolor, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Haircolor> findAll();

	Optional<Haircolor> findByname(String name);


}


