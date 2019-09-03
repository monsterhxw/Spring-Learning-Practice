package geektime.spring.controller.controller;

import geektime.spring.controller.controller.exception.FormValidationException;
import geektime.spring.controller.controller.request.CoffeeRequest;
import geektime.spring.controller.model.Coffee;
import geektime.spring.controller.service.CoffeeService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path = "/coffee")
@RequiredArgsConstructor(onConstructor = @_({@Autowired}))
@Slf4j
public class CoffeeController {

    private final CoffeeService coffeeService;

    @GetMapping(params = "!name")
    @ResponseBody
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }

    //    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Coffee getById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffee(id);
        log.info("Coffee: {}", coffee);
        return coffee;
    }

    @GetMapping(params = "name")
    @ResponseBody
    public Coffee getByName(@RequestParam String name) {
        return coffeeService.getCoffee(name);
    }


    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffee(@Valid CoffeeRequest newCoffee, BindingResult result) {
        if (result.hasErrors()) {
            // 这里先简单处理，后续讲到异常处理时会改
            log.warn("Binding Errors: {}", result);
            throw new FormValidationException(result);
        }
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    //    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffeeWithoutBindingResult(@Valid CoffeeRequest newCoffee) {
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addJsonCoffee(@Valid @RequestBody CoffeeRequest newCoffee,
        BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Binding Errors: {}", result);
            throw new ValidationException(result.toString());
        }
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.CREATED)
//    public Coffee addJsonCoffeeWithoutBindingResult(@Valid @RequestBody CoffeeRequest newCoffee) {
//        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
//    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file) {
        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String text;
                while (null != (text = reader.readLine())) {
                    String[] arr = StringUtils.split(text, " ");
                    if (null != arr && 2 == arr.length) {
                        coffees.add(coffeeService.saveCoffee(
                            arr[0],
                            Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(arr[1]))));
                    }
                }
            } catch (IOException e) {
                log.error("Exception", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return coffees;
    }
}
