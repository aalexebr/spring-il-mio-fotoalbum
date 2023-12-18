<script>
import axios from 'axios';

//import

   export default {
    data() {
      return{
        formData:{
            object: "",
            content:"",
            sender:"",
            photoAuthor:"admin",
        },
		sentMessage:false
      }
    },
    methods: {
        sendMessage(){
          axios.post("http://127.0.0.1:8080/api/message/create",this.formData)
            .then(data=>{
              console.log(data.status)
			  if(data.status == 200){
				this.sentMessage = true
			  }
					
            })
        },
    },
    components: {
     
    },
    props:{
      artist:String
    },
    
  }
</script>


<template>

    <main>
        <div class="container p-5 position-relative">
			<div v-if="sentMessage" class="card position-absolute top-50 start-50 translate-middle">
				<h3>mess sent!</h3>
			</div>

			<template v-if="!sentMessage">
				<form @submit.prevent="sendMessage" >
					<div class="mb-3">
						<label for="object" class="form-label">object of message</label>
						<input type="text" class="form-control" id="object" v-model="formData.object">
					</div>
					<div class="mb-3">
						<label for="content" class="form-label">content</label>
						<textarea v-model="formData.content" class="form-control" id="content"></textarea>
					</div>
					<div class="mb-3">
						<label for="sender" class="form-label">from</label>
						<input type="text" class="form-control" id="sender" v-model="formData.sender">
					</div>
					<button type="submit" class="btn btn-primary">Submit</button>
				</form>
			</template>
            
            <button @click="back">back</button>
		</div>
    </main>

</template>




<style lang="scss" scoped>

</style>