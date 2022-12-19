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
                    <th scope="col">
                        <button class="btn btn-success">CREA PIZZA</button>
                    </th>
                    <div class="bg-dark text-white">
                        <form @submit="createNewPizza" class="w-100">
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
                </tr>
            </thead>
            <tbody>
                <tr v-for="pizza in pizzas" :key="pizza.id">
                    <th scope="row">{{ pizza.id }}</th>
                    <td>{{ pizza.name }}</td>
                    <td>{{ pizza.description }}</td>
                    <td>{{ pizza.price }}</td>
                    <td>
                        <button   class="btn btn-warning" @click="getIngredienti(pizza.id)">INGREDIENTI</button>
                        <div class="text-white" v-for="ingredient in pizza.ingredients" :key="ingredient.id">
                           ciao <h1>{{ ingredient.name }}</h1>
                        </div>
                    </td>
                    <td>
                         <button class="btn btn-danger" @click="deletePizza(pizza.id)">DELETE</button>
                    </td>
                        <div class="bg-dark text-white">
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
                                <input type="submit" value="save" class="btn btn-success" @click="editPizza(pizza.id)">
                            </form>
                        </div>
                </tr>
                
            </tbody>
        </table>
      </div>
      
  </div>
</template>

<script>
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
      });
    },
    //!INGREDIENT METHODS
     getIngredienti(id) {
      axios.get(API_URL + '/ingredienti/by/pizza/' + id)
           .then(res => {
            //ritorniamo l'ingredienti associati
            const ingredients = res.data;
            
            if (ingredients == null) return;
            const index = this.getPizzaIndexById(id);
            this.pizzas[index].ingredients = ingredients;
            
        });
     }
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
    });
  }
  
}
</script>

<style>

</style>