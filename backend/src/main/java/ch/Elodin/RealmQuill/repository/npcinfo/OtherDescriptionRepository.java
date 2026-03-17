package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.OtherDescription;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherDescriptionRepository extends JpaRepository<OtherDescription, Integer> {
        @SuppressWarnings("null")
				@Override
	List<OtherDescription> findAll();

	Optional<OtherDescription> findBydescription(String description);


}


