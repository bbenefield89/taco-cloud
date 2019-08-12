package sia.tacocloud;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "tacos.orders")
public class OrderProps {

    int pageSize = 20;

}
