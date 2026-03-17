package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Ideals;
import ch.Elodin.RealmQuill.repository.npcinfo.IdealsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Ideals")
public class IdealsController extends GenericController<Ideals, Integer> {
    public IdealsController(IdealsRepository repository) {
        super(repository);
    }
}




