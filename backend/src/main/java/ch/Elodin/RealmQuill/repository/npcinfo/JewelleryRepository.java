package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.Jewellery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JewelleryRepository extends JpaRepository<Jewellery, Integer> {
        @SuppressWarnings("null")
				@Override
	List<Jewellery> findAll();

	Optional<Jewellery> findByname(String name);


}


