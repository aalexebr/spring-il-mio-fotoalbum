<script>
import VueCookies from 'vue-cookies';
import HeaderComponent from '../../components/HeaderComponent.vue';
import FooterComponent from '../../components/FooterComponent.vue';
// import { store } from '../store.js';
import axios from 'axios';
import { router } from '../../router';
axios.defaults.withCredentials = true;
export default{
    data(){
      return{
		// store,
		formData:{
			email:'',
			password:''
		},
		showPassword: false,
		//auth:false,
		user: null
	  }
    },
    components: {
		HeaderComponent,
      	FooterComponent,
    }, 
    methods:{
      
		login(){
			axios.post('http://127.0.0.1:8000/api/login',this.formData,{
				headers:{
					'Content-Type': 'multipart/form-data'
				}
			})
				.then(res=>{
					console.log('data',res.data)
					VueCookies.set('jwt',res.data.token,'1hr')
					console.log('cookie',VueCookies.get('jwt'))
					// this.token = res.data.access_token
					// console.log('token',this.token)
					//this.auth = true

					// 💢 aggiungi un if
					if(res.data.isAdmin == 1){
						router.push('/admin-dashboard')
					}
					else{
						router.push('/guest-dashboard')
					}
                    
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

	<div class="login">

		<div class="form-login">
			<form @submit.prevent="login">
				<h4 class="pb-2 text-center fw-bold">
					Login
				</h4>
				<div class="text-center social">
					<small class="pb-1">
						Accedi con:
					</small>
					<!-- <div class="icons">
						<i class="fa-brands fa-facebook"></i>
						<i class="fa-brands fa-google"></i>
						<i class="fa-brands fa-linkedin"></i>
                	</div> -->
				</div>
				<hr>

				<div class="mb-2 mt-4">
					<label class="form-label" for="email">Email</label>
					<input class="form-control" type="email" name="email" id="email" v-model="formData.email" placeholder="Inserisci la tua email.." required>
				</div>
				<div class="mb-4 input-password">
					<label class="form-label" for="password">Password</label>
					<input class="form-control " type="password" id="password" name="password"   v-model="formData.password" :inputmode="showPassword ? 'text' : 'password'" placeholder="Inserisci la tua password.." required>
					<span @click="togglePasswordVisibility()" class="eyes-button">
						<i :class="showPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
					</span>
				</div>

				<button class="button-login mt-2" type="submit">Login</button>
			</form>

			<div class="banner-sign-up">
				<h4>
					Non sei iscritto?
				</h4>
				<span>
					<router-link :to= "{name: 'register'}" class="text-decoration-none text-white">clicca qui</router-link>
				</span> 
			</div>
		</div>
	</div>

	<!-- <FooterComponent/> -->

</template>

<style lang="scss" scoped>
@use '../scss/variables.scss' as *;


.login{
	height: calc(100vh - 50px);
	background-color: #bbdbae;
	display: flex;
	justify-content: center;
	align-items: center;

	.form-login{
		background-color: white;
		//padding: 40px 20px;
		display: flex;

		.social{
			position: relative;
			height: 25px;
			.icons{
				position: absolute;
				bottom: -15px;
				left: 42px;
				font-size: 1.5em;
				color: white;
				display: flex;

				>i{
					font-size: 0.5em;
					background-color: #932441;
					border-radius: 50%;
					margin: 0 5px;
					padding: 3px;
				}
			}
		}
		form{
			padding: 20px 20px;
		}
		.link-color{
			color: #932441;
		}

		.form-control{
			font-size: 0.7em;
		}

		label{
			font-size: 0.8em;
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
			font-size: 0.8em;
		}

		.button-login{
			background-color: #932441;
			color: white;
			border: none;
			padding: 2px 15px;
			font-size: 0.8em;
			border-radius: 10px;
		}

		.banner-sign-up{
			background-color: #932441;
			width: 150px;
			//background-color:#303854;
			overflow-x: hidden;
			overflow-y: hidden;
			background-image: url(../img/wave-dashboard.png);
			background-position: bottom;
			background-size: cover;
			position: relative;
			text-align: center;
			display: flex;
			flex-direction: column;
			justify-content: center;

			h4{
				color: white;
			}
			span{
				background-color:#303854;
				color: white;
				border: none;
				padding: 2px 15px;
				font-size: 0.8em;
				border-radius: 10px;
			}
		}
	}
}

</style>