<script>
//import

   export default {
    data() {
      return{
        title:""
      }
    },
    methods: {
    },
    components: {
        
    },
    props:{
      photo:Object
    }
    
    
  }
</script>


<template>
	<div v-if="photo !=null" class="container">

	
		<div class="img">
			<img :src="photo.url">
		</div>
		<div>
			title: {{ photo.title }}
		</div>
		<p>
			description:
			<br>
			{{ photo.description }}
		</p>
		<div>
			by: {{ photo.user.username }}
		</div>


	</div>
	
</template>



<style lang="scss" scoped>
@use '../assets/scss/variables.scss' as *;

</style>