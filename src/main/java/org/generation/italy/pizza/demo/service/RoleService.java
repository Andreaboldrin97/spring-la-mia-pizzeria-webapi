package org.generation.italy.pizza.demo.service;

import java.util.List;

import org.generation.italy.pizza.demo.pojo.Role;
import org.generation.italy.pizza.demo.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//indichiamo che qesta classe ci servirà da service
@Service
public class RoleService {
	
	//indichiamo la dipendenza da iniettare
	@Autowired
	private RoleRepo roleRepo;


	//funzione per salvare/inserire un record
	public void save(Role role) {
		
		//grazie all'interfaccia JpaRepository possiamo usare il method save
		roleRepo.save(role);
	}
	
	//funzione per prendere tutti i record
	public List<Role> findAll() {
			
		//ritorniamo una lista di record
		return roleRepo.findAll();
	}
}
