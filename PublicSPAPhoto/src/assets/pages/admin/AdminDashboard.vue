<script>
import VueCookies from 'vue-cookies';
// import { store } from '../store.js';
import axios from 'axios';
import { router } from '../../../router';
import AsideComp from '../../../components/AsideAdminComp.vue';

axios.defaults.withCredentials = true;

export default{
    data(){
      return{
		loadUser : false,
		// store,
		formData:{
			email:'',
			password:''
		},
		auth:false,
		user: null,
	  }
    },
    components: {
		AsideComp
    }, 
    methods:{
      
		getUser(){
			this.loadUser = true
			let token = VueCookies.get('jwt')
			axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
			axios.get('http://127.0.0.1:8000/api/admin/user')
				.then(res=>{
					this.user = res.data.user
					console.log('user', this.user)
					this.loadUser = false
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
    },
	created() {
		this.getUser();
	}
}
</script>

<template>

	<div class="dashboard">
		
		<!-- aside -->
		<AsideComp/>
		<!-- inside -->
		<div class="dashboard-inside">
			<template v-if="loadUser">
				.....loading
			</template>

			<div v-if="loadUser == false ">
				
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
@use '../../scss/variables.scss' as *;


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
		background-color:#303854;
        overflow-x: hidden;
        overflow-y: hidden;
		background-image: url(../img/wave-dashboard.png);
		background-position: bottom;
		background-size: cover;
		position: relative;

		header{
			//background-color: #303854;
			color: white;
			display: flex;
			align-items: center;
			padding-left: 10px;
			height: 30px;
			margin-bottom: 20px;
			padding-top: 30px;
		}
        .links{
			margin-top: 50px;

			a{
				font-size: 0.8em;
				margin-left: 10px;
				color: white;
			}
		}

		.logout{
			position: absolute;
			bottom: 45px;
			padding-left: 10px;
			//background-color: #256EA7;
			color: #256EA7;
			border-radius: 5px;
			font-size: 0.9em;
			text-align: center;
			display: flex;
			align-items: center;
		}

		.logout:hover{
			background-color: #256EA7;
			color: white;
			padding-right: 10px;
		}
    }

    .dashboard-inside{
        width: 90%;
		height: 100vh;
		overflow-y: auto;

		.home-page{
			margin-top: 30px;
			text-align: center;
		}
    }
    
}
</style>