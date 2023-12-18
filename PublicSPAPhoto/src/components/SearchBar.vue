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
    <header class="container py-5">
      <div>
        <input type="text" v-model="title">
        <button @click="this.$emit('searchTitle',title,categoriesSelected)">search</button>
      </div>
	  <div class="p-2">
		<label for="cars">select a category:</label>

		<select name="categories" id="categories" v-model="categoriesSelected">
			<option value="" selected>none</option>
			<option v-for="(cat, i) in categories" :key="i" :value="cat.name">
			{{ cat.name }}
			</option>
        </select>
	  </div>


    </header>
</template>



<style lang="scss" scoped>
@use '../assets/scss/variables.scss' as *;

</style>