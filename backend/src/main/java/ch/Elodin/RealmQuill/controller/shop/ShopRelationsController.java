package ch.Elodin.RealmQuill.controller.shop;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.shop.ShopRelations;
import ch.Elodin.RealmQuill.repository.shop.ShopRelationsRepository;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5137")
@RestController
@RequestMapping("/api/ShopRelations")
public class ShopRelationsController extends GenericController<ShopRelations, Integer> {

    public ShopRelationsController(ShopRelationsRepository repository) {
        super(repository);
    }
}



