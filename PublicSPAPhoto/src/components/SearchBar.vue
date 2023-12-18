<script>
//import

   export default {
    data() {
      return{
        title:"",
		categoriesSelected:{}
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
	computed : {
		categoryIds() {
      return this.categories.reduce((obj, cat) => {
        obj[cat.id] = false; // Initialize with false
        return obj;
      }, {});
    }
  },
  watch: {
    // Watch for changes in the categoryIds and update categoriesSelected
    categoryIds: {
      handler(newVal) {
        this.categoriesSelected = newVal;
      },
      immediate: true // Trigger the watch immediately to set the initial value
    }
  },
    
    
  }
</script>


<template>
    <header class="container">
      <div>
        <input type="text" v-model="title">
        <button @click="this.$emit('searchTitle',title)">search</button>
      </div>
	  <div v-for="(cat,i) in categories" :key="i">
		<input v-model="categoriesSelected[i]"
			type="checkbox" :id="`category-${cat.id}`" :value="cat.name" :name="cat.name">
		<label :for="`category-${cat.id}`">{{ cat.name }}</label>
	  </div>
        <button @click="test">click</button>
    </header>
</template>



<style lang="scss" scoped>
@use '../assets/scss/variables.scss' as *;

</style>