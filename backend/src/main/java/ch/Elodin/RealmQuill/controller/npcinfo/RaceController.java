package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Race;
import ch.Elodin.RealmQuill.repository.npcinfo.RaceRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Race")
public class RaceController extends GenericController<Race, Integer> {
    public RaceController(RaceRepository repository) {
        super(repository);
    }
}




