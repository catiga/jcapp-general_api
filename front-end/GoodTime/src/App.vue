<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
import {mapActions} from 'vuex'

export default {
  beforeRouterEnter(to,form,next) {
    this.getLogo();
    next();
  },
  methods: {
    ...mapActions(['getLogo']),
  }
}
</script>


<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }
  ul,li {
    margin: 0;
    padding: 0;
    list-style: none;
  }
  img {
    display: block;
    width: 100%;
  }


</style>