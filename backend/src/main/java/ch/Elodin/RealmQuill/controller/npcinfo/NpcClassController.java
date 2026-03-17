package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.NpcClass;
import ch.Elodin.RealmQuill.repository.npcinfo.KlasseRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/NpcClass")
public class NpcClassController extends GenericController<NpcClass, Integer> {
    public NpcClassController(KlasseRepository repository) {
        super(repository);
    }
}




