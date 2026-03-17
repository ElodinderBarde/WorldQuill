package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Background;
import ch.Elodin.RealmQuill.repository.npcinfo.BackgroundRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/background")
public class BackgroundController extends GenericController<Background, Integer> {
    public BackgroundController(BackgroundRepository repository) {
        super(repository);
    }
}



