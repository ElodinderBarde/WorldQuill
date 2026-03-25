package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Stats;
import ch.Elodin.RealmQuill.repository.npcinfo.StatsRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController @RequestMapping("/api/stats")
public class StatsController extends GenericController<Stats, Integer> {
    public StatsController(StatsRepository repository) { super(repository); }
}