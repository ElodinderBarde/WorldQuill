package ch.Elodin.RealmQuill.repository.shop;
import ch.Elodin.RealmQuill.model.shop.ShopEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopEmployeeRepository extends JpaRepository<ShopEmployee, Integer> {
}
