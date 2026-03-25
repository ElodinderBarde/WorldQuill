package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Family;
import ch.Elodin.RealmQuill.repository.npcinfo.FamilyRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/family")
public class FamilyController extends GenericController<Family, Integer> {
    public FamilyController(FamilyRepository repository) {
        super(repository);
    }
}
