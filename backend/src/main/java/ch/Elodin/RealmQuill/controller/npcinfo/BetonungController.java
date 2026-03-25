package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Betonung;
import ch.Elodin.RealmQuill.repository.npcinfo.BetonungRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Betonung")
public class BetonungController extends GenericController<Betonung, Integer> {
    public BetonungController(BetonungRepository repository) {
        super(repository);
    }
}



