package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Armor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmorRepository extends JpaRepository<Armor, Integer> {

        @SuppressWarnings("null")
				@Override
	List<Armor> findAll();


	Optional<Armor> findByacvalue(int acvalue);


}


