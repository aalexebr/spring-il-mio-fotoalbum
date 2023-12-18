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
	<div v-if="photo !=null" class="container py-5">

		<div class="border p-2 d-flex flex-column justify-content-center">
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
		
		<button @click="$emit('back')" class="me-2">back</button>
		<button @click="$emit('contact',photo.user.username)">contact artist</button>
	</div>
	
</template>



<style lang="scss" scoped>
@use '../assets/scss/variables.scss' as *;
img{
	width: 200px;
}

</style>