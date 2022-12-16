package org.generation.italy.pizza.demo.service;

import java.util.List;
import java.util.Optional;

import org.generation.italy.pizza.demo.pojo.Drink;
import org.generation.italy.pizza.demo.pojo.Pizza;
import org.generation.italy.pizza.demo.repo.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// indichiamo che qesta classe ci servirà da service
@Service
public class PizzaService {

	//indichiamo la dipendenza da iniettare
	@Autowired
	private PizzaRepo pizzaRepo;
	
	//funzione per salvare/inserire un record
	public void save(Pizza pizza) {
		
		//grazie all'interfaccia JpaRepository possiamo usare il method save
		pizzaRepo.save(pizza);
	}
	
	//funzione per prendere tutti i record
	public List<Pizza> findAll() {
		
		//ritorniamo una lista di record
		return pizzaRepo.findAll();
	}
	
	//funzione per recuperare un record specifico in base all'id
		//utilizzeremo l'Optional<Pizza> per controllare se un valore è presente o non 
	public Optional<Pizza> findPizzaByID(int id){
		
		return pizzaRepo.findById(id);
	}
	
	//funzione per l'elimanazione di un record
	public void delete(Pizza pizza) {
		//grazie all'interfaccia JpaRepository possiamo usare il method delete
		pizzaRepo.delete(pizza);
	}
	
	//funzione di ricerca by name
	public List<Pizza> findByName(String name) {
		//ritorniamo una lista di record
		return pizzaRepo.findBynameContainingIgnoreCase(name);
	}
}
