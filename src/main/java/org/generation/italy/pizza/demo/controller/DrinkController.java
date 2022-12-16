package org.generation.italy.pizza.demo.controller;

import java.util.List;
import java.util.Optional;

import org.generation.italy.pizza.demo.pojo.Drink;
import org.generation.italy.pizza.demo.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

//indichiamo che questa classe ci servirà da controller dei drink
@Controller
public class DrinkController {
	
	//indichiamo la dipendenza da iniettare
	@Autowired
	private DrinkService drinkService;
	
	//metodo per ritornare l'index dei drink
	@GetMapping("/drink")
	public String getDrink(Model model) {
		
		//assegnamo ad un lista i record del db
		List<Drink> drinks = drinkService.findAll();
		
		model.addAttribute("drinks", drinks);
		
		return "drinkCRUD/index";
	}
	
	//metodo per ritornare la show del drink
	@GetMapping("user/drink/{id}")
	public String getShowDrink(@PathVariable("id") int id, Model model) {
			
		// selezioniamo il record con quell'id
		Optional<Drink> optDrink= drinkService.findDrinkById(id);
		Drink drink = optDrink.get();
		//portiamo il record alla pagina in modo da indicare le collone che lo compongono
		model.addAttribute("drink", drink);
			
		return "drinkCRUD/show";
	}
	
	//metodo per creare un record
	@GetMapping("admin/drink/create")
	public String createDrink(Model model) {
		
		//creiamo un istanza del record da creare 
		Drink drink = new Drink();
		//portiamo il record alla pagina in modo da indicare le collone che lo compongono
		model.addAttribute("drink", drink);
		//a quale view fa riferimento
		return "drinkCRUD/create";
	}
	@PostMapping("admin/drink/create")              
	public String storeDrink(@Valid Drink drink,
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
			return "redirect:admin/drink/create";
		}
		
		try {
			//metodo per salvare un record
			drinkService.save(drink);
		//prima di salvare controllo la presenza dei duplicati
		}catch(Exception e) {
			
			//porto l'errore del duplicato in console
			redirectAttributes.addFlashAttribute("uniqueException", e.getMessage() );
			return "redirect:admin/drink/create";
		}
		
		//a quale view ritorna
		return "redirect:/drink";
	}
	
	//metodo per modificare un record
	@GetMapping("admin/drink/edit/{id}")
	public String editDrink(@PathVariable("id") int id, Model model) {
		
		// selezioniamo il record con quell'id
		Optional<Drink> optDrink= drinkService.findDrinkById(id);
		Drink drink = optDrink.get();
		//portiamo il record alla pagina in modo da indicare le collone che lo compongono
		model.addAttribute("drink", drink);
		
		return "drinkCRUD/update";
	}
	@PostMapping("admin/drink/store")
	public String updateDrink(@Valid Drink drink,
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
		return "redirect:admin/drink/store";
		}

		try {
			//metodo per salvare un record
			drinkService.save(drink);
		//prima di salvare controllo la presenza dei duplicati
		}catch(Exception e) {
			
			//porto l'errore del duplicato in console
			redirectAttributes.addFlashAttribute("uniqueException", e.getMessage() );
			return "redirect:admin/drinks/store";
		}
		
		//a quale view ritorna
		return "redirect:/drink";
	}
	
	//metodo per la delete
	@GetMapping("admin/drink/delete/{id}")
	public String deleteDrink(@PathVariable("id") int id, Model model) {
		// selezioniamo il record con quell'id
		Optional<Drink> optDrink= drinkService.findDrinkById(id);
		Drink drink = optDrink.get();
		
		//metodo per salvare un record
		drinkService.delete(drink);
		
		//a quale view ritorna
		return "redirect:/drink";
		
	}
	
}
