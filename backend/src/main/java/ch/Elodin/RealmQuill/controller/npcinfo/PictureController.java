package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Picture;
import ch.Elodin.RealmQuill.repository.npcinfo.PictureRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Picture")
public class PictureController extends GenericController<Picture, Integer> {
    public PictureController(PictureRepository repository) {
        super(repository);
    }
}




