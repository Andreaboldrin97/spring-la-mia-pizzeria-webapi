package org.generation.italy.pizza.demo.repo;

import org.generation.italy.pizza.demo.pojo.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//indichiamo a quale tabella fa riferimento l'interfaccia 
@Repository
public interface PromotionRepo extends JpaRepository<Promotion, Integer>{
	
}
	
