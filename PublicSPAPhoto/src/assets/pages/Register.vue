<script>
import VueCookies from 'vue-cookies'
// import { store } from '../store.js';
import axios from 'axios';
import { router } from '../../router';
import HeaderComponent from '../../components/HeaderComponent.vue';
import FooterComponent from '../../components/FooterComponent.vue';
axios.defaults.withCredentials = true;
export default{
    data(){
      return{
		// store,
		formData:{
            name:'',
            surname:'',
			phone_number: '',
			email:'',
			password:''
		},
		showPassword: false,
		auth:false,
		user: null
	  }
    },
    components: {
		HeaderComponent,
      	FooterComponent,
    }, 
    methods:{
      
		login(){
            console.log(this.formData)
			axios.post('http://127.0.0.1:8000/api/register',this.formData,{
				headers:{
					'Content-Type': 'multipart/form-data'
				}
			})
				.then(res=>{
					console.log('data',res.data)
					// VueCookies.set('jwt',res.data.access_token,'1hr')
					// console.log('cookie',VueCookies.get('jwt'))
					// this.token = res.data.access_token
					// console.log('token',this.token)
					// this.store.auth = true

                    // 💥 decidi se andare in dashboard o al login di nuovo
                    router.push('/guest-dashboard')
				})
				.catch(err=>{
					console.log(err)
				})
		},
		getUser(){
			let token = VueCookies.get('jwt')
			axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
			axios.get('http://127.0.0.1:8000/api/user')
				.then(res=>{
					console.log('user',res)
					this.user = res.data
				})
				.catch(err=>{
					console.log(err)
				})
		},
		refresh(){
			let token = VueCookies.get('jwt')
			axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
			axios.post('http://127.0.0.1:8000/api/refresh')
			.then(res=>{
				console.log(res)
				VueCookies.remove('jwt')
				
				VueCookies.set('jwt',res.data.access_token,'1hr')
				// this.token= res.data.access_token

			})
		},
		togglePasswordVisibility() {
			this.showPassword = !this.showPassword;
			const input = document.getElementById('password');
			if (input.type === "password") {
			input.type = "text";
			} else {
			input.type = "password";
			}
		},
    }
}
</script>

<template>

	<HeaderComponent/>

	<div class="register">
		<div class="form-login">
			<h4 class="pb-2">
				Register
			</h4>
			<form @submit.prevent="login">
				<div class="mb-2">
					<label class="form-label" for="name">Nome</label>
					<input class="form-control" type="text" id="name" name="name" placeholder="Inserisci il tuo nome.." required v-model="formData.name">
				</div>
				<div class="mb-2">
					<label class="form-label" for="surname">Cognome</label>
					<input class="form-control" type="text" id="surname" name="surname" placeholder="Inserisci il tuo cognome.." required v-model="formData.surname">
				</div>
				<div class="mb-2">
					<label class="form-label" for="phone_number">Telefono</label>
					<input class="form-control" type="tel" id="phone_number" name="phone_number" placeholder="Inserisci il tuo numero.." required v-model="formData.phone_number">
				</div>
				<div class="mb-2">
					<label class="form-label" for="email">Email</label>
					<input class="form-control" type="email" name="email" id="email" placeholder="Inserisci la tua email.." required v-model="formData.email">
				</div>
				<div class="mb-4 input-password">
					<label class="form-label" for="password">Password</label>
					<input class="form-control " type="password" id="password" name="password"   v-model="formData.password" :inputmode="showPassword ? 'text' : 'password'" placeholder="Inserisci la tua password.." required>
					<span @click="togglePasswordVisibility()" class="eyes-button">
						<i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
					</span>
				</div>

				<h6>
					Sei un professionista? 
					<router-link :to= "{name: 'register-admin'}"  class="text-decoration-none link-color" >clicca qui</router-link>
				</h6>

				<button class="button-login mt-2" type="submit">Submit</button>
			</form>
		</div>
	</div>

<!-- <FooterComponent/> -->

</template>

<style lang="scss" scoped>

@use '../scss/variables.scss' as *;


.register{
	//height: calc(100vh - 50px);
	padding: 30px;
	background-color: #bbdbae;
	display: flex;
	justify-content: center;
	align-items: center;

	.form-login{
		background-color: white;
		padding: 20px 20px;

		.link-color{
			color: #932441;
		}

		.form-control{
			font-size: 0.7em;
		}

		.input-password{
			position: relative;

			.eyes-button{
				position: absolute;
				bottom: 3px;
				right: 7px;
				i{
					font-size: 0.7em;
				}
			}
		}

		h6{
			font-size: 0.9em;
		}

		.button-login{
			background-color: #932441;
			color: white;
			border: none;
			padding: 5px 10px;
			font-size: 0.8em;
			border-radius: 5px;
		}
	}
}

</style>