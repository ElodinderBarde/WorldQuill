package ch.Elodin.RealmQuill.repository.npcinfo;
import ch.Elodin.RealmQuill.model.npcinfo.Subclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubclassRepository extends JpaRepository<Subclass, Integer> {

    @Query("SELECT s FROM Subclass s WHERE s.npcClass.classname = :classname")
    List<Subclass> findByClassname(@Param("classname") String classname);

    @Query("SELECT s FROM Subclass s WHERE s.npcClass.id = :classId")
    List<Subclass> findByClassId(@Param("classId") int classId);
}
