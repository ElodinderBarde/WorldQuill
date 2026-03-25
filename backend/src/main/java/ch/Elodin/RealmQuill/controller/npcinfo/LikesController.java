package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Likes;
import ch.Elodin.RealmQuill.repository.npcinfo.LikesRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Likes")
public class LikesController extends GenericController<Likes, Integer> {
    public LikesController(LikesRepository repository) {
        super(repository);
    }
}




