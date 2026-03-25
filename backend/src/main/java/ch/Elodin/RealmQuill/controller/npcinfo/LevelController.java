package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Level;
import ch.Elodin.RealmQuill.repository.npcinfo.LevelRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Level")
public class LevelController extends GenericController<Level, Integer> {
    public LevelController(LevelRepository repository) {
        super(repository);
    }
}




