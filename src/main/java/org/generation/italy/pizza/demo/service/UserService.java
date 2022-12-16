package org.generation.italy.pizza.demo.service;

import java.util.List;
import java.util.Optional;

import org.generation.italy.pizza.demo.pojo.User;
import org.generation.italy.pizza.demo.repo.UserRepo;
import org.generation.italy.pizza.demo.security.DatabaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//indichiamo che qesta classe ci servirà da service
@Service
public class UserService implements UserDetailsService {
	
	//indichiamo la dipendenza da iniettare
	@Autowired
	private UserRepo userRepo;
	
	//funzione per salvare/inserire un record
	public void save(User user) {
		
		//grazie all'interfaccia JpaRepository possiamo usare il method save
		userRepo.save(user);
	}
	
	//funzione per prendere tutti i record
	public List<User> findAll() {
			
		//ritorniamo una lista di record
		return userRepo.findAll();
	}

	//metodo dell'UserDetailsService
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//recuperiamo l'user in base al suo username
		Optional<User> userOpt = userRepo.findByUserName(username);
		
		//se è vuoto lanciamo un eccezzione
		if (userOpt.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		
		//ritorniamo l'user per verificare l'identità
		return new DatabaseUserDetails(userOpt.get());
	}
}
