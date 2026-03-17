package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Flaw;
import ch.Elodin.RealmQuill.repository.npcinfo.FlawRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Flaw")
public class FlawController extends GenericController<Flaw, Integer> {
    public FlawController(FlawRepository repository) {
        super(repository);
    }
}




