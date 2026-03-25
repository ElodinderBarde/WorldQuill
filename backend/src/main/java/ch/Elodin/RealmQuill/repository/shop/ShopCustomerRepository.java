package ch.Elodin.RealmQuill.repository.shop;
import ch.Elodin.RealmQuill.model.shop.ShopCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopCustomerRepository extends JpaRepository<ShopCustomer, Integer> {
}
