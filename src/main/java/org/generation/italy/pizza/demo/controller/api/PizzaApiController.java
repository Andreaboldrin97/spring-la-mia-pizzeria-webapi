package org.generation.italy.pizza.demo.controller.api;

import java.util.List;

import org.generation.italy.pizza.demo.pojo.Pizza;
import org.generation.italy.pizza.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/1/pizza")
@CrossOrigin("*")
public class PizzaApiController {

	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping("/all")
	public List<Pizza> getAll() {
		
		List<Pizza> allPizza = pizzaService.findAll();
		return allPizza ;
	}
	
	@PostMapping("/create")
	public Pizza createPizza(@Valid @RequestBody Pizza pizza) {
		
		System.err.println(pizza);
		return pizzaService.save(pizza);
	}
}
