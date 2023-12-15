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
		loadMess: false,
		// store,
		messages : null,
		auth:false,
		user: null,
		messageNotRead: false,
	  }
    },
    components: {
		AsideComp
    }, 
    methods:{
        getMessages(){
			this.loadMess = true;
            let token = VueCookies.get('jwt')
			axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
			axios.get('http://127.0.0.1:8000/api/admin/messages')
				.then(res=>{
					this.messages= res.data.messages
					console.log('data', res.data)
					this.loadMess = false;
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

		readMessages(){
			return this.messageNotRead = true;
		}
    },
	created() {
		this.getMessages();
	}
}
</script>

<template>

	<div class="dashboard">
		
		<AsideComp/>
		<!-- inside -->
		<div class="dashboard-inside">
			<template v-if="loadMess">
				.............loading
			</template>
			<div v-if="loadMess == false" class="container mt-5">
				<table class="table table-hover">
					<thead>
						<tr>
							<!-- if email o messaggi? -->
							<th scope="col">Email</th>
							<th scope="col">Messaggi</th>
						</tr>
					</thead>
					<thead>
						<tr>
							<th scope="col">Nome</th>
							<th scope="col">Email</th>
							<th scope="col">Testo</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="message in messages" @click="readMessages()" >
							<td>{{ message.guest.name }}</td>
							<td>{{ message.guest.email }}</td>
							<td>{{ message.message}}</td>
							<td>
								<span class="pe-2">
									<i v-if="messageNotRead == false" class="fa-solid fa-envelope"></i>
									<i v-else class="fa-solid fa-envelope-open"></i>
								</span>
								<i class="fa-solid fa-trash text-danger"></i>
							</td>
						</tr>
					</tbody>
				</table>
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

		.table > :not(caption) > * > * {
			background-color: #bbdbae !important;
		}
		.table tbody tr:hover{
			background-color: #256EA7;
		}

		.table tbody tr{
			font-size: 0.9em;
		}
    }
    
}
</style>