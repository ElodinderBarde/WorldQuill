package ch.Elodin.RealmQuill.controller.ruf;
import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.ruf.Reputation;
import ch.Elodin.RealmQuill.repository.ruf.ReputationRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/reputation")
public class ReputationController extends GenericController<Reputation, Integer> {
    public ReputationController(ReputationRepository repository) { super(repository); }
}