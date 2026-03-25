package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Jewellery;
import ch.Elodin.RealmQuill.repository.npcinfo.JewelleryRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/Jewellery")
public class JewelleryController extends GenericController<Jewellery, Integer> {
    public JewelleryController(JewelleryRepository repository) {
        super(repository);
    }
}




