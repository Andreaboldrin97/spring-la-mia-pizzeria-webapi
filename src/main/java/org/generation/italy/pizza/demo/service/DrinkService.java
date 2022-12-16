package org.generation.italy.pizza.demo.service;

import java.util.List;
import java.util.Optional;

import org.generation.italy.pizza.demo.pojo.Drink;
import org.generation.italy.pizza.demo.repo.DrinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkService {

	//importiamo la repo
	@Autowired
	private DrinkRepo drinkRepo;
	
	//funzioni per la manipolazioni dei record
	
	//funzione per salvare/inserire un record
	public void save(Drink drink) {
		//grazie all'interfaccia JpaRepository possiamo usare il method save
		drinkRepo.save(drink);
	}
	
	//funzione per prendere tutti i record
	public List<Drink> findAll() {
		//ritorniamo una lista di record
		return drinkRepo.findAll();
	}
	
	//funzione per trovare un record by id
	//utilizzeremo l'Optional<Drink> per controllare se un valore è presente o non 
	public Optional<Drink> findDrinkById(int id){
		
		return drinkRepo.findById(id);
	}
	
	//funzione per eliminare un record
	public void delete(Drink drink) {
		//grazie all'interfaccia JpaRepository possiamo usare il method delete
		drinkRepo.delete(drink);
	}
	
	//funzione di ricerca by name
	public List<Drink> findByName(String name) {
		//ritorniamo una lista di record
		return drinkRepo.findBynameContainingIgnoreCase(name);
	}
}
