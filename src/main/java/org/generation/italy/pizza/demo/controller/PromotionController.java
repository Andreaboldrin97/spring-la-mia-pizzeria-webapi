package org.generation.italy.pizza.demo.controller;

import java.util.List;

import org.generation.italy.pizza.demo.pojo.Pizza;
import org.generation.italy.pizza.demo.pojo.Promotion;
import org.generation.italy.pizza.demo.service.PizzaService;
import org.generation.italy.pizza.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;


@Controller
public class PromotionController {

	//indichiamo la dipendenza da iniettare
		@Autowired
		private PizzaService pizzaService;
		
		@Autowired
		private PromotionService promotionService;
		
		//INDEX PROMOTION
			//Indichiamo a quale path fa riferimento questo metodo
			@GetMapping("/promotion")
			public String getPromotions(Model model) {
				//assegnamo ad un lista i record del db
				List<Promotion> promotions = promotionService.findAllPizza();
				
				model.addAttribute("promotions", promotions);
				
				//a quale view fa riferimento
				return "promotionCRUD/index";
			}
			
		//CREATE PROMOTION
			@GetMapping("admin/promotion/create")
			public String createPromotion(Model model) {
				
				Promotion promotion = new Promotion();
				model.addAttribute("promotion", promotion);
				
				List<Pizza> allPizza = pizzaService.findAll();
				model.addAttribute("allPizza", allPizza);
				
				return "promotionCRUD/create";
			}
			@PostMapping("admin/promotion/store")
			public String storePromotion(@Valid @ModelAttribute("promotion") Promotion promotion,
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
				return "admin/promotion/create";
				
				}
				//metodo per otterere le pizze inserite
				List<Pizza> allPizzaChoise = promotion.getAllPizza();
				for (Pizza pizza : allPizzaChoise ) {
					//inserisco le pizze da salvare
					pizza.setPromotion(promotion);
					
				}
				
				//metodo per salvare un record
				promotionService.save(promotion);
				
				//a quale view ritorna
				return "redirect:/promotion";
			}
			
			
}
