package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Armor;
import ch.Elodin.RealmQuill.repository.npcinfo.ArmorRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Armor")
public class ArmorController extends GenericController<Armor, Integer> {
    public ArmorController(ArmorRepository repository) {
        super(repository);
    }
}



