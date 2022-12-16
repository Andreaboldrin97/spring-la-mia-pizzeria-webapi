package org.generation.italy.pizza.demo.pojo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table
public class Promotion {

	//variabili d'istanza
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private LocalDate startingDate;
	
	@NotNull
	private LocalDate endDate;
	
	@NotNull
	@Column(unique = true)
	private String title;
	
	
	@OneToMany(mappedBy = "promotion", cascade = CascadeType.ALL)
	private List<Pizza> allPizza;
	
	//costrutti 
	public Promotion() {}
	
	public Promotion(LocalDate startingDate, LocalDate endDate, String title) {
		
		setStartingDate(startingDate);
		setEndDate(endDate);
		setTitle(title);
	}

	//get & set
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getStartingDate() {
		return startingDate;
	}
	public void setStartingDate(LocalDate startingDate) {
		this.startingDate = startingDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	//get & set join
	public List<Pizza> getAllPizza() {
		return allPizza;
	}

	public void setAllPizza(List<Pizza> allPizza) {
		this.allPizza = allPizza;
	}
	
	@Override
	public String toString() {
		return "(" + getId() + ")" + " " + getTitle() + " - " + getStartingDate() + " " + getEndDate();
	}
	
}
