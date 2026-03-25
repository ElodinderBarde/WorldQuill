package ch.Elodin.RealmQuill.controller.world;
import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.world.GovernmentType;
import ch.Elodin.RealmQuill.repository.world.GovernmentTypeRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/government-type")
public class GovernmentTypeController extends GenericController<GovernmentType, Integer> {
    public GovernmentTypeController(GovernmentTypeRepository repository) { super(repository); }
}