package org.generation.italy.pizza.demo.controller;

import java.util.List;
import java.util.Optional;

import org.generation.italy.pizza.demo.pojo.Drink;
import org.generation.italy.pizza.demo.pojo.Ingredient;
import org.generation.italy.pizza.demo.pojo.Pizza;
import org.generation.italy.pizza.demo.pojo.Promotion;
import org.generation.italy.pizza.demo.service.IngredientService;
import org.generation.italy.pizza.demo.service.PizzaService;
import org.generation.italy.pizza.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

//indichiamo che qesta classe ci servirà da controller
@Controller
public class PizzaController {
	
	//indichiamo la dipendenza da iniettare
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private IngredientService ingredientService;

	//GET PATH without parameters
		
		//INDEX PIZZA
		//Indichiamo a quale path fa riferimento questo metodo
		@GetMapping("/allPizzas")
		public String getPizze(Model model) {
			//assegnamo ad un lista i record del db
			List<Pizza> allPizzas = pizzaService.findAll();
			
			model.addAttribute("allPizzas", allPizzas);
			
			//a quale view fa riferimento
			return "pizzaCRUD/index";
		}
		
		//metodo per ritornare la show del drink
		@GetMapping("user/pizza/{id}")
		public String getShowDrink(@PathVariable("id") int id, Model model) {
				
			// selezioniamo il record con quell'id
			Optional<Pizza> optPizza = pizzaService.findPizzaByID(id);
			Pizza pizza  = optPizza.get();
			model.addAttribute("pizza", pizza);
					
			return "pizzaCRUD/show";
		}
		
		//CREATE PIZZA
		//Indichiamo a quale path fa riferimento questo metodo
		@GetMapping("/admin/pizza/create")
		public String createPizza(Model model) {
			
			Pizza pizza = new Pizza();
			model.addAttribute("pizza", pizza);
			
			List<Promotion> promotions = promotionService.findAll();
			model.addAttribute("promotions", promotions);
			
			List<Ingredient> ingredients = ingredientService.findAll();
			model.addAttribute("ingredients", ingredients);
			
			//a quale view fa riferimento
			return "pizzaCRUD/create";
		}
		@PostMapping("/admin/pizza/create")
		public String storePizza(@Valid @ModelAttribute("pizza") Pizza pizza,
				//Intergaccia per la registrazione degli errori 
				BindingResult bindingResult, 
				//Interfaccia secondaria di Model per passare attributi
				RedirectAttributes redirectAttributes) {

			//veriafichiamo la presenza di errori nella compilazione dei campi del form
			//hasErrors() ci ritorna un valore booleano sulla presenza o no di errori
			if(bindingResult.hasErrors()) {
			
			//riportiamo gli errori all'interno della view indicata
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			//ritorniamo al form con gli errori se i dati sono errati
			return "redirect:/admin/pizza/create";
			
			}
			
			//metodo per salvare un record
			pizzaService.save(pizza);
			
			//a quale view ritorna
			return "redirect:/allPizzas";
		}
		
		//EDIT PIZZA
		//Indichiamo a quale path fa riferimento questo metodo
		@GetMapping("/admin/pizza/edit/{id}")
		public String editPizza(@PathVariable("id") int id, Model model) {
			
			// selezioniamo il record con quell'id
			Optional<Pizza> optPizza = pizzaService.findPizzaByID(id);
			Pizza pizza  = optPizza.get();
			model.addAttribute("pizza", pizza);
			
			//importo anche la lista delle promozioni
			List<Promotion> promotions = promotionService.findAll();
			model.addAttribute("promotions", promotions);
			
			List<Ingredient> ingredients = ingredientService.findAll();
			model.addAttribute("ingredients", ingredients);
			
			//a quale view fa riferimento
			return "pizzaCRUD/update";
		}
		@PostMapping("/admin/pizza/store")
		public String updatePizza(@Valid @ModelAttribute("pizza") Pizza pizza,
				//Intergaccia per la registrazione degli errori 
				BindingResult bindingResult, 
				//Interfaccia secondaria di Model per passare attributi
				RedirectAttributes redirectAttributes) {

			//veriafichiamo la presenza di errori nella compilazione dei campi del form
			//hasErrors() ci ritorna un valore booleano sulla presenza o no di errori
			if(bindingResult.hasErrors()) {
			
			//riportiamo gli errori all'interno della view indicata
			redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
			
			//ritorniamo al form con gli errori se i dati sono errati
			return "redirect:/admin/pizza/store";
			
			}
			//metodo per salvare un record
			pizzaService.save(pizza);
			
			//a quale view ritorna
			return "redirect:/allPizzas";
		}
		
		//DELETE PIZZA 
		//Indichiamo a quale path fa riferimento questo metodo
		@GetMapping("/admin/pizza/delete/{id}")
		public String deletePizza(@PathVariable("id") int id) {
			
			// selezioniamo il record con quell'id
			Optional<Pizza> optPizza = pizzaService.findPizzaByID(id);
			Pizza pizza  = optPizza.get();
			//metodo per eliminare un record
			pizzaService.delete(pizza);
			
			//a quale view ritorna
			return  "redirect:/allPizzas";
		}
		
		
}
