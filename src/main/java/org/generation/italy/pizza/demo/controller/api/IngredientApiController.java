package org.generation.italy.pizza.demo.controller.api;

import java.util.List;

import org.generation.italy.pizza.demo.pojo.Ingredient;
import org.generation.italy.pizza.demo.pojo.Pizza;
import org.generation.italy.pizza.demo.service.IngredientService;
import org.generation.italy.pizza.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/ingredient")
@CrossOrigin("*")
public class IngredientApiController {

	@Autowired
	private IngredientService ingredienteService;
	
	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping("/all")
	public List<Ingredient> getIngredienti() {
		
		return ingredienteService.findAll();
	}
	
	@GetMapping("/by/pizza/{id}")
	public List<Ingredient> getIngredientiByPizzaId(@PathVariable("id") int id) {
		
		Pizza pizza = pizzaService.findPizzaByID(id).get();
		System.err.println(pizza.getIngredients());
		return pizza.getIngredients();
	}
	
}
