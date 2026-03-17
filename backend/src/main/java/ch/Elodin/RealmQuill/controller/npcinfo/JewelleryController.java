package ch.Elodin.RealmQuill.controller.npcinfo;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.npcinfo.Jewellery;
import ch.Elodin.RealmQuill.repository.npcinfo.JewelleryRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Jewellery")
public class JewelleryController extends GenericController<Jewellery, Integer> {
    public JewelleryController(JewelleryRepository repository) {
        super(repository);
    }
}




