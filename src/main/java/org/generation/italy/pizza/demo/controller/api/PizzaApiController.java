package org.generation.italy.pizza.demo.controller.api;

import java.util.List;
import java.util.Optional;

import org.generation.italy.pizza.demo.pojo.Pizza;
import org.generation.italy.pizza.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@PostMapping("/edit/{id}")
	public Pizza editPizza(@PathVariable("id") int id , @Valid @RequestBody Pizza pizza) {
		System.err.println(pizza);
		//prendiamo la pizza che si trova nel db
		Pizza oldPizza = pizzaService.findPizzaByID(id).get();
		//assegnamo alla pizza modificata gli stessi ingredienti
		pizza.setIngredients(oldPizza.getIngredients());
		
		return pizzaService.save(pizza);
	}
	
	@GetMapping("/delete/{id}")
	public Boolean deletePizza(@PathVariable("id") int id) {
			
		try {
			//prendiamo la pizza che si trova nel db
			Pizza pizza = pizzaService.findPizzaByID(id).get();
			pizzaService.delete(pizza);
		} catch(Exception e) { 
			return false; 
		}
		
		return true;
		
	}
	
	@GetMapping("/search/{query}")
	public List<Pizza> getSearchPizza(@PathVariable("query") String query) {
		
		//assegnamo ad un lista i record del db
		List<Pizza> allPizza = query == null ? pizzaService.findAll() : pizzaService.findByName(query);
		return allPizza ;
	}

}
