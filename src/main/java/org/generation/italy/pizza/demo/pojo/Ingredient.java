package org.generation.italy.pizza.demo.pojo;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private String name;
	
	//creiamo la relazione con la promozzioni con le pizze
	@ManyToMany(mappedBy = "ingredients")
	private List<Pizza> pizza;
	
	//COSTRUCTS
	// indichiamo il costruttore di default
	public Ingredient() {}
	
	//creiamo il costruttore 
	public Ingredient(String name) {
		setName(name);
	}
		
	//creiamo il costruttore + pizza
	public Ingredient(String name, Pizza ... pizzas) {
		this(name);
		setPizza(Arrays.asList(pizzas));
	}

	// get & set
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Pizza> getPizza() {
		return pizza;
	}
	public void setPizza(List<Pizza> pizza) {
		this.pizza = pizza;
	}
	
	@Override
	public String toString() {
		return "(" + getId() + ")" + getName() ;
	}
	
	
}
