package ch.Elodin.RealmQuill.controller.ruf;
import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.ruf.ReputationConflict;
import ch.Elodin.RealmQuill.model.ruf.ReputationConflictKey;
import ch.Elodin.RealmQuill.repository.ruf.ReputationConflictRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/reputation-conflict")
public class ReputationConflictController extends GenericController<ReputationConflict, ReputationConflictKey> {
    public ReputationConflictController(ReputationConflictRepository repository) { super(repository); }
}