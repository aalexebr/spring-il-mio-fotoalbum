<script>
//import

import axios from 'axios';

   export default {
    data() {
      return{
        currentPage:null,
        photos:null,
        totPages:null
      }
    },
    components: {

    },
    methods:{
      getPhotos(){
        axios.get("http://127.0.0.1:8080/api/index")
          .then(data=>{
            console.log(data.data.content)
            console.log(data.data.pageable)
            this.totPages = data.data.totalPages
            this.currentPage = data.data.pageable.pageNumber
            }
          )
      }

    },
    created(){
      this.getPhotos()
    }
  }
</script>



<template>
  <div>
    hello 
  </div>
</template>




<style lang="scss">
@use 'assets/scss/main.scss';




</style>
