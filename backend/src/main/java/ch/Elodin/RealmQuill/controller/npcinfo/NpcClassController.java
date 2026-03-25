package ch.Elodin.RealmQuill.controller.npcinfo;
import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.NpcClass;
import ch.Elodin.RealmQuill.repository.npcinfo.NpcClassRepository;

import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/npc-class")
public class NpcClassController extends GenericController<NpcClass, Integer> {
    public NpcClassController(NpcClassRepository repository) {
        super(repository);
    }
}
