package ch.Elodin.RealmQuill.repository.npcinfo;

import ch.Elodin.RealmQuill.model.npcinfo.Subclass;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubclassRepository extends JpaRepository<Subclass, Integer> {

	@Query("SELECT s FROM Subclass s WHERE LOWER(s.class_ID.classname) = LOWER(:classname)")
	List<Subclass> findByClassname(String classname);


	@Query("SELECT s FROM Subclass s WHERE s.class_ID.class_ID = :classId")
	List<Subclass> findByClassId(int classId);





}



