package org.generation.italy.pizza.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.generation.italy.pizza.demo.pojo.Drink;
import org.generation.italy.pizza.demo.pojo.Pizza;
import org.generation.italy.pizza.demo.pojo.inter.PriceableInterface;
import org.generation.italy.pizza.demo.service.DrinkService;
import org.generation.italy.pizza.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {


	//HOME
	//Indichiamo a quale path fa riferimento questo metodo
	@GetMapping("/")
	//indichiamo il metodo publico che ritornerà una Stringa
						//indichiamo la presenza di un model per fornire attributi alla view ritornata
	public String getHome(Model model) {
		//assegnamo alla variabile text un valore 
		String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
					+ "	Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
					+ "	Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
		//aggiungiamo il valore della variabile al posto dell'elemento text inserito nell'html
		model.addAttribute("text", text);
		
		//ritorniamo il file assocciato al nome "home"
		return "home";
	}
	
	//indichiamo la dipendenza da iniettare
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private DrinkService drinkService;
	
	//metodo per la ricerca by name
	@GetMapping("/search")
	public String findByName(Model model,
								// @RequestParam per estrarre parametri di query, 
								@RequestParam(name = "query", required=false) String query) {
			
		System.err.println(query);
		//utilizziamo un ternario per verificare la presenza di una query
		List<Drink> drinks = query == null ? drinkService.findAll() : drinkService.findByName(query);
		//portiamo i record alla pagina
		model.addAttribute("drinks", drinks);
		model.addAttribute("query", query);
			
		//assegnamo ad un lista i record del db
		List<Pizza> allPizzas = query == null ? pizzaService.findAll() : pizzaService.findByName(query);
		//portiamo i record alla pagina
		model.addAttribute("allPizzas", allPizzas);
			
		return "searching";
	}
	
	// all index product
	@GetMapping("/allProduct")
	public String getAllProduct(Model model) {
		
		List<PriceableInterface> allProduct = new ArrayList<>();
		
		allProduct.addAll(pizzaService.findAll());
		allProduct.addAll(drinkService.findAll());
		
		// ordinamento by lambda
		allProduct.sort((e1, e2) -> e1.getPrice() - e2.getPrice());
		
		//portiamo i record alla pagina
		model.addAttribute("allProduct", allProduct);
		
		return "product";
	}
	
}
