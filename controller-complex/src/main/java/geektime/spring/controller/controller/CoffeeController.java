package geektime.spring.controller.controller;

import geektime.spring.controller.model.Coffee;
import geektime.spring.controller.service.CoffeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/coffee")
@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping(params = "!name")
    @ResponseBody
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Coffee getById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffee(id);
        return coffee;
    }

    @GetMapping(params = "name")
    @ResponseBody
    public Coffee getByName(@RequestParam String name) {
        return coffeeService.getCoffee(name);
    }

}
