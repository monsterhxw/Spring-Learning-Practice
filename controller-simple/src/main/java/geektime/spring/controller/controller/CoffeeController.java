package geektime.spring.controller.controller;

import geektime.spring.controller.model.Coffee;
import geektime.spring.controller.service.CoffeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/coffee")
@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping
    @ResponseBody
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }
}
