<template>
  <div>
      <h1>PIZZe</h1>
  </div>
</template>

<script>
//importiamo axios
import axios from 'axios';
//costabte url
const API_URL = "http://localhost:8080/api/1";
export default {
  data() {
    return {
       pizze : [],
    };
  },
  methods: {


  },
    mounted() {
     axios.get(API_URL + '/pizza/all')
          .then(res => {
     
         const pizze = res.data;
         if (pizze == null) return;
         this.pizze = pizze;
         console.log(this.pizze);
    });
   
  }
}
</script>

<style>

</style>