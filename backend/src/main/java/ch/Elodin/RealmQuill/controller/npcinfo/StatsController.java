package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Stats;
import ch.Elodin.RealmQuill.repository.npcinfo.StatsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Stats")
public class StatsController extends GenericController<Stats, Integer> {
    public StatsController(StatsRepository repository) {
        super(repository);
    }
}




