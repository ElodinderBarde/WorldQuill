package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Picture;
import ch.Elodin.RealmQuill.repository.npcinfo.PictureRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Picture")
public class PictureController extends GenericController<Picture, Integer> {
    public PictureController(PictureRepository repository) {
        super(repository);
    }
}




