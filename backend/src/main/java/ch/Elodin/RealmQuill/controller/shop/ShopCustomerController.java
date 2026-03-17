package ch.Elodin.RealmQuill.controller.shop;

import ch.Elodin.RealmQuill.controller.GenericController;
import ch.Elodin.RealmQuill.model.shop.ShopCustomer;
import ch.Elodin.RealmQuill.repository.shop.ShopCustomerRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ShopCustomer")
public class ShopCustomerController extends GenericController<ShopCustomer, Integer> {
    public ShopCustomerController(ShopCustomerRepository repository) {
        super(repository);
    }
}




