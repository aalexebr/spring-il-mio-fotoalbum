<script>
import VueCookies from 'vue-cookies';
// import { store } from '../store.js';
import axios from 'axios';
import { router } from '../../router';
import AsideComp from '../../components/AsideGuestComp.vue';
axios.defaults.withCredentials = true;
export default{
    data(){
      return{
		// store,
		formData:{
			email:'',
			password:''
		},
		auth:false,
		user: null,
		profile: false,
		calendario: false,
		messaggi: false
	  }
    },
    components: {
		AsideComp
    }, 
    methods:{
      
		login(){
			axios.post('http://127.0.0.1:8000/api/login',this.formData,{
				headers:{
					'Content-Type': 'multipart/form-data'
				}
			})
				.then(res=>{
					console.log('data',res.data.access_token)
					VueCookies.set('jwt',res.data.access_token,'1hr')
					console.log('cookie',VueCookies.get('jwt'))
					// this.token = res.data.access_token
					// console.log('token',this.token)
					this.store.auth = true
                    router.push('/home')
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
					this.user = res.data.user
					console.log('user', this.user)
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
		takeProfile(){
			this.profile = true;

			if(this.calendario == true || this.messaggi == true){
				this.calendario = false;
				this.messaggi = false;
			}
		},
		takeCalendario(){
			this.calendario = true;

			if(this.profile == true || this.messaggi == true){
				this.profile = false;
				this.messaggi = false;
			}
		},
		takeMessaggi(){
			this.messaggi = true;

			if(this.profile == true || this.calendario == true){
				this.profile = false;
				this.calendario = false;
			}
		}
    },
	created() {
		this.getUser();
	}
}
</script>


<template>

	<div class="dashboard">
		<!-- aside -->
		<!-- <aside>
			<header>
				logo
			</header>

			<a @click="takeProfile()">
				Profilo
			</a>
			<br>
			<a @click="takeCalendario()">
				Calendario
			</a>
			<br>
			<a @click="takeMessaggi()">
				Messaggi
			</a>
			<br>
			<a>
				Servizi
			</a>
			<br>
			<a>
				Statistiche
			</a>
			<br>
			<a>
				Options
			</a>
		</aside> -->
		<AsideComp/>
		<!-- inside -->
		<div class="dashboard-inside">

			<div v-if="(messaggi || profile || calendario)">
			
				<div :class="(profile) ? 'd-block' : 'd-none'">
					Profilo page
				</div>
	
				<div :class="(calendario) ? 'd-block' : 'd-none'">
					Calendario page
				</div>
	
				<div :class="(messaggi) ? 'd-block' : 'd-none'">
					Messaggi page
				</div>

			</div>









			<!-- potrebbe trasformarsi in homepage e fare una variabile anche per questa -->
			<div v-else>
				
				<div class="home-page">
					<h4>
						Benvenuta/o nel tuo profilo {{ user.name }}!
					</h4>
					<p>
						qui puoi controllare bla bla bla
					</p>
				</div>

			</div>
		</div>
    </div>
</template>


<style lang="scss" scoped>
@use '../scss/variables.scss' as *;


.dashboard{
    padding: 0;
    margin: 0;
    display: flex;
    height: 100vh;
    background-color:#bbdbae;

    >*{
        padding: 0;
    }
    
    aside{
        width: 10%;
        height: 100vh;
		background-color:#33987e;
        overflow-x: hidden;
        overflow-y: hidden;

		header{
			background-color: #303854;
			color: white;
			display: flex;
			align-items: center;
			padding-left: 10px;
			height: 30px;
			margin-bottom: 20px;
		}
        
		a{
			font-size: 0.8em;
			margin-left: 10px;
			color: white;
		}
    }

    .dashboard-inside{
        width: 90%;

		.home-page{
			margin-top: 30px;
			text-align: center;
		}
    }
    
}
</style>