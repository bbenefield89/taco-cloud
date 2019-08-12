package sia.tacocloud.data;

import org.springframework.data.repository.CrudRepository;
import sia.tacocloud.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByZip(String zip);

    List<Order> readOrdersByZipAndPlacedAtBetween(String zip, Date startDate, Date endDate);

    //    List<Order> findByZipAndPlacedAtBetween(String zip, Date startDate, Date endDate);
}
