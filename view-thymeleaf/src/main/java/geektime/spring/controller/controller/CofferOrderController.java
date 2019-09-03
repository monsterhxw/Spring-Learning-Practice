package geektime.spring.controller.controller;

import geektime.spring.controller.controller.request.OrderRequest;
import geektime.spring.controller.model.Coffee;
import geektime.spring.controller.model.CoffeeOrder;
import geektime.spring.controller.service.CoffeeOrderService;
import geektime.spring.controller.service.CoffeeService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

//@RestController
@Controller
@RequestMapping(path = "/order")
@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
@Slf4j
public class CofferOrderController {

    private final CoffeeOrderService orderService;
    private final CoffeeService coffeeService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody OrderRequest newOrder) {
        log.info("Receive new Order: {}", newOrder);
        Coffee[] coffees = coffeeService.getCoffeeByName(newOrder.getItems()).toArray(new Coffee[]{});
        return orderService.createOrder(newOrder.getCustomer(), coffees);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CoffeeOrder getOrder(@PathVariable("id") Long id) {
        return orderService.get(id);
    }

    @ModelAttribute
    public List<Coffee> coffees() {
        return coffeeService.getAllCoffee();
    }

    @GetMapping
    public ModelAndView showCreateForm() {
        return new ModelAndView("create-order-form");
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createOrder(@Valid OrderRequest newOrder, BindingResult result, ModelMap map) {
        if (result.hasErrors()) {
            log.warn("Binding Result: {}", result);
            map.addAttribute("message", result.toString());
            return "create-order-form";
        }
        log.info("Receive new Order: {}", newOrder);
        Coffee[] coffees = coffeeService.getCoffeeByName(newOrder.getItems()).toArray(new Coffee[]{});
        CoffeeOrder order = orderService.createOrder(newOrder.getCustomer(), coffees);
        return "redirect:/order/" + order.getId();
    }
}
