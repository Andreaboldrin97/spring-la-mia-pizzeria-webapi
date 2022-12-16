package org.generation.italy.pizza.demo.service;

import java.util.List;
import java.util.Optional;

import org.generation.italy.pizza.demo.pojo.Ingredient;
import org.generation.italy.pizza.demo.repo.IngredientRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class IngredientService {

	//indichiamo la dipendenza da iniettare
	@Autowired
	private IngredientRepo ingredientRepo;
	
	//funzione per salvare/inserire un record
			public void save(Ingredient  ingredient) {
				
				//grazie all'interfaccia JpaRepository possiamo usare il method save
				ingredientRepo.save(ingredient);
			}
			
			//funzione per prendere tutti i record
			public List<Ingredient> findAll() {
				
				//ritorniamo una lista di record
				return ingredientRepo.findAll();
			}
			
			//funzione per recuperare un record specifico in base all'id
				
			public Optional<Ingredient> findIngredientByID(int id){
				
				return ingredientRepo.findById(id);
			}
			
			//funzione per l'elimanazione di un record
			public void delete(Ingredient ingredient) {
				//grazie all'interfaccia JpaRepository possiamo usare il method delete
				ingredientRepo.delete(ingredient);
			}
			
			//funzione per le pizze correlate
			
			//usiamo questa annotation per mantere il canele aperto tra il db e le query
			@Transactional
			public List<Ingredient> findAllPizza() {
				
				//inserisco in una lista tutti gli ingredienti
				List<Ingredient> ingredients = ingredientRepo.findAll();
				
				
				for(Ingredient ingredient : ingredients) {
					
					//usiamo questa annotazione per creare la query al db
					Hibernate.initialize(ingredient.getPizza());
				}
				
				//ritorno la stista con la join
				return ingredients;
			}
}
