<template>
  <div>
      <h1 >ALL MY PIZZA VUE</h1>
      <div class="mx-3">
          <table class="table table-dark table-hover">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">NOME</th>
                    <th scope="col">DESCRIZIONE</th>
                    <th scope="col">PREZZO</th>
                    <th scope="col"></th>
                     <th scope="col"></th>
                    <th scope="col">
                        <button class="btn btn-success" @click="createBoolean = true" v-if="!createBoolean">CREA PIZZA</button>
                        <div class="bg-dark text-white" v-else>
                            <form @submit="createNewPizza" class="w-100">
                                CREA UNA NUOVA PIZZA..
                                <div class="mb-3">
                                    <label class="form-label">Nome pizza</label>
                                    <input class="form-control" type="text" name="name" v-model="pizza_create.name">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Descrizione pizza</label>
                                    <input class="form-control" type="text" name="description" v-model="pizza_create.description">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Prezzo pizza</label>
                                    <input class="form-control" type="number" name="price" v-model="pizza_create.price">
                                </div>
                                <input type="submit" value="save" class="btn btn-success">
                            </form>
                        </div>
                    </th>
                   
                </tr>
            </thead>
            <tbody>
                <tr v-for="pizza in pizzas" :key="pizza.id">
                    <th scope="row">{{ pizza.id }}</th>
                    <td>{{ pizza.name }}</td>
                    <td>{{ pizza.description }}</td>
                    <td>{{ pizza.price }}</td>
                    <td>
                        <ul class="card-text mb-2" v-if="pizza.ingredients">
                            <div v-if="pizza.ingredients.length > 0">
                                <li v-for="ingredient in pizza.ingredients" :key="ingredient.id">{{ ingredient.name }} </li>
                            </div>
                            <div v-else>
                                non sono presenti ingredienti
                            </div>
                        </ul>
                        <button v-else @click="getIngredients(pizza.id)" class="btn btn-success me-1">Ingredienti</button>              
                    </td>
                    <td>
                         <button class="btn btn-danger" @click="deletePizza(pizza.id)">DELETE</button>
                    </td>
                    <td>
                        <button class="btn btn-primary" @click="editBoolean = true" v-if="!editBoolean">EDIT</button>
                        <div class="bg-dark text-white" v-else>
                            <form @submit="updatePizza" class="w-100">
                            <div class="mb-3">
                                    <label class="form-label">Nome pizza</label>
                                    <input class="form-control" type="text" name="name" v-model="pizza.name">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Descrizione pizza</label>
                                    <input class="form-control" type="text" name="description" v-model="pizza.description">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Prezzo pizza</label>
                                    <input class="form-control" type="number" name="price" v-model="pizza.price">
                                </div>
                                <input type="submit" value="EDIT" class="btn btn-primary" @click="editPizza(pizza.id)">
                            </form>
                        </div>
                    </td>
                        
                </tr>
                
            </tbody>
        </table>
      </div>
      
  </div>
</template>

<script>
 //import Vue from 'vue'
//importiamo axios
import axios from 'axios';
//costabte url
const API_URL = "http://localhost:8080/api/1";
//costante per assegrare l'id
const PIZZA_ID = -1;

export default {
  data() {
    return {
       pizzas : [],
       pizza_id : PIZZA_ID,
       pizza_create : {},
       createBoolean: false,
       editBoolean: false,
    };
  },
  methods: {
      //!PIZZA METHODS
    //metodo che recupera' l'index della pizza 
    getPizzaIndexById(id) {
        for (let x=0; x<this.pizzas.length; x++) {
            const pizza = this.pizzas[x];
            if (pizza.id == id){
                return x;
            }
        }
        return -1;
    },
    //metodo per recuperare l'elemento by id
    getPizzaById(id) {
      return this.pizzas[this.getPizzaIndexById(id)];
    },

    //metodo create
    createNewPizza(e){
        e.preventDefault();

        axios.post(API_URL + '/pizza/create', this.pizza_create)
          .then(res => {
                const pizza = res.data;
                //se la pizza è null blocca l'eseguzione
                if (pizza == null) return;
                //aggiungiamo l'elemento all'array
                this.pizzas.push(pizza)
            }).catch(error => {
          console.log(error)
        })
    },

    //metodo per modificare 
    editPizza(id){
        this.pizza_id = id;
    },
    updatePizza(e){
        e.preventDefault();

        let id = this.pizza_id;
        const pizza = this.getPizzaById(id);

        axios.post(API_URL + '/pizza/edit/'+ id, pizza )
          .then(res => {  

            const index = this.getPizzaIndexById(id);
            const oldPizza = this.pizzas[index];
            const pizza = res.data;
            pizza.ingredients = oldPizza.ingredients;
            this.pizzas[index] = pizza;
        }).catch(error => {
          console.log(error)
        })
    },
    //metodo per eliminare il record
    deletePizza(id){
         axios.get(API_URL + '/pizza/delete/'+ id )
         .then(res => {
            
            const deleted = res.data;
            if (!deleted) return;
            //eliminiamo la pizza dall'array senza ricaricare la pagina
            const index = this.getPizzaIndexById(id);
            this.pizzas.splice(index, 1);
        }).catch(error => {
            console.log(error)
            });
    },
    //!INGREDIENT METHODS
     getIngredients(pizzaId) {
      axios.get(API_URL + "/ingredient/by/pizza/" + pizzaId)
        .then(response => {
            const ingredients = response.data
            if (ingredients == null) return
          //recupero l'index dell'elemento
            const index = this.getPizzaIndexById( pizzaId);
          //recupro l'elemento nell'array by index
            const pizza = this.pizzas[index];
          //aggiungo gli ingredienti
            pizza.ingredients = ingredients; 
          //sostituisco il vecchio elemento con quello nuovo aggiornato 
            this.pizzas.splice(index, 1 , pizza);
            console.log(this.pizzas[index].ingredients);
        })
        .catch(error => {
          console.log(error)
        })
    },

  },
    mounted() {
     //!PIZZA METHODS
    //recuperiamo tutte le pizze
     axios.get(API_URL + '/pizza/all')
          .then(res => {
     
         const allPizza = res.data;
         //se la pizza è null blocca l'eseguzione
         if (allPizza == null) return;
         this.pizzas = allPizza;
         console.log(this.pizzas);
    }).catch(error => {
          console.log(error)
        });
  }
  
}
</script>

<style>

</style>