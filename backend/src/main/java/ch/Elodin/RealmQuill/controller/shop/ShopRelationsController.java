package ch.Elodin.RealmQuill.controller.shop;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.shop.ShopRelations;
import ch.Elodin.RealmQuill.repository.shop.ShopRelationsRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ShopRelations")
public class ShopRelationsController extends GenericController<ShopRelations, Integer> {

    public ShopRelationsController(ShopRelationsRepository repository) {
        super(repository);
    }
}



