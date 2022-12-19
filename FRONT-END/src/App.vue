<template>
  <div id="app">
    <PizzaHome></PizzaHome>
  
  </div>
</template>

<script>
import PizzaHome from './components/PizzaHome.vue'

export default {
  name: 'App',
  components: {
    PizzaHome,
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
