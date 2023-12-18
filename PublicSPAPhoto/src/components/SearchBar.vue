<script>
//import

   export default {
    data() {
      return{
        title:"",
		categoriesSelected:null
      }
    },
    methods: {
		test(){
			console.log(this.categoriesSelected)
		}
    },
    components: {
        
    },
    props:{
      categories:Array
    },
 
  }
</script>


<template>
    <header class="container">
      <div>
        <input type="text" v-model="title">
        <button @click="this.$emit('searchTitle',title,categoriesSelected)">search</button>
      </div>
	  <div>
		<label for="cars">select a category:</label>

		<select name="categories" id="categories" v-model="categoriesSelected">
        <!-- <option disabled value="">Please select one</option> -->
        <option v-for="(cat, i) in categories" :key="i" :value="cat.name">
          {{ cat.name }}
        </option>
      </select>
	  </div>
        <!-- <button @click="test">click</button> -->
    </header>
</template>



<style lang="scss" scoped>
@use '../assets/scss/variables.scss' as *;

</style>