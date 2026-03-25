package ch.Elodin.RealmQuill.repository.npcinfo;
import ch.Elodin.RealmQuill.model.npcinfo.Jackets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JacketsRepository extends JpaRepository<Jackets, Integer> {
}
