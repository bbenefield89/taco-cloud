package sia.tacocloud.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacocloud.Order;
import sia.tacocloud.OrderProps;
import sia.tacocloud.User;
import sia.tacocloud.data.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderRepository orderRepository;
    private int pageSize;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderProps orderProps) {
        this.orderRepository = orderRepository;
        this.pageSize = orderProps.getPageSize();
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        log.info("\n\n\n\n" + pageSize + "\n\n\n\n\n");
        model.addAttribute("order", new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        log.info("Order Submitted: " + order);

        order.setUser(user);
        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

}
