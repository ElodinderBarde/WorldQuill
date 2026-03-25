package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Hairstyle;
import ch.Elodin.RealmQuill.repository.npcinfo.HairstyleRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Hairstyle")
public class HairstyleController extends GenericController<Hairstyle, Integer> {
    public HairstyleController(HairstyleRepository repository) {
        super(repository);
    }
}




