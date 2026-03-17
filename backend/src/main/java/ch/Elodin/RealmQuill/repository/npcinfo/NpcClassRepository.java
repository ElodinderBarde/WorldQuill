package ch.Elodin.RealmQuill.repository.npcinfo;



import ch.Elodin.RealmQuill.model.npcinfo.NpcClass;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NpcClassRepository extends JpaRepository<NpcClass, Integer> {
    @SuppressWarnings("null")
    @Override
    List<NpcClass> findAll();

    Optional<NpcClass> findByClassname(String classname);


}


