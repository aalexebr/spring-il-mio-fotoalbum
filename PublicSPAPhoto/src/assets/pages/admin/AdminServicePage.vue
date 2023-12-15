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
		// store,
		messages : null,
		auth: false,
		user: null,
        price: 0
        
	  }
    },
    components: {
		AsideComp
    }, 
    methods:{
        getUser(){
			let token = VueCookies.get('jwt')
			axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
			axios.get('http://127.0.0.1:8000/api/admin/user')
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
    },
	created() {
        this.getUser();
	}
}

</script>

<template>
    <div class="dashboard">
		
		<AsideComp/>
		<!-- inside -->
		<div class="dashboard-inside">

            <div class="layover">
                <h4>
                    Servizi
                </h4>
                <p>
                    Ecco i servizi che vuoi proporre ai tuoi clienti:
                </p>


                <div class="servizi">
                    <table class="table table-hover">
					<thead>
						<tr>
							<th scope="col">Nome</th>
							<th scope="col">Tempo</th>
							<th scope="col">Prezzo</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="service in services">
							<td>{{ service.name }}</td>
							<td>{{ service.time_slot }}</td>
							<td>{{ service.price}}</td>
							<td>
                                <!-- per modificare -->
                                <i class="fa-solid fa-pencil"></i>
                                <!-- per eliminare -->
								<i class="fa-solid fa-trash text-danger"></i>
							</td>
						</tr>
					</tbody>
				</table>
                </div>

                <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom">Aggiungi</button>

                <!-- 💢 al click si apre offcanvas per creare il servizio -->

                <div class="offcanvas offcanvas-bottom" tabindex="-1" id="offcanvasBottom" aria-labelledby="offcanvasBottomLabel">
                <div class="offcanvas-header">
                    <h6 class="offcanvas-title" id="offcanvasBottomLabel">
                        Aggiungi un servizio
                    </h6>
                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                </div>
                    <div class="offcanvas-body">

                        <!-- qui ci sarà il form che si collega alla tabella dei servizi per aggiugerli e anche modificarli -->
                        <form action="">
                            <div class="container">

                                <!-- name -->
                                <div class="imput-group mb-3">
                                    <label for="name">Nome del Servizio</label>
                                    <input type="text" class="form-control" id="name" name="name"  required>
                                </div>
    
                                <!-- time
                                <div class="imput-group mb-3">
                                    <label form="time_slot">Tempo</label>
                                        <input type="time" class="form-control" id="time_slot" name="time_slot" required>
                                </div> -->
    
                                <!-- time_slot -->
                                <div class="imput-group mb-3">
                                    <label for="name">Tempo del Servizio</label>
                                    <!-- ▶ qui sarà dinamico -->
                                    <select class="form-select form-select-sm" aria-label="Small select example" required>
                                        <option selected>Scegli il tempo</option>
                                        <option value="1">0.30 min</option>
                                        <option value="2">1h</option>
                                        <option value="3">1.15h</option>
                                        <option value="3">1.30h</option>
                                        <option value="3">2h</option>
                                    </select>
                                </div>
    
                                <!-- prezzo -->
                                <div class="imput-group mb-3">
                                    <label for="price">Prezzo</label>
                                    <br>
                                    <div class="d-flex align-items-center">
                                        <input type="range" id="price" v-model="price" name="price" min="0" max="50" step="1" required>
                                        <span class="ps-2" id="priceValue">{{ price }}€</span>
                                    </div>
                                </div>
                            </div>


                            <button type="submit">
                                Aggiungi
                            </button>
                        </form>
                    </div>
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
		background-image: url(../img/wave-dashboard.png);
		background-position: bottom;
		background-size: cover;
    }

    .dashboard-inside{
        width: 90%;
		height: 100vh;
		overflow-y: auto;

        .layover{
            width: 80%;
            margin-top: 30px;
            margin-bottom: 30px;
            // background-color: rgba($color: #000000, $alpha: 0.2);
        }
        .offcanvas.offcanvas-bottom {
            height: 350px;
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


@media screen and (min-width: 320px) {
}


       
@media screen and (min-width: 375px) {

}


    
@media screen and (min-width: 425px) {
    
}

@media screen and (min-width: 576px) {
    
}


/* Tablet */

@media screen and (min-width: 768px){
}


/* Desktop */
@media screen and (min-width: 992px){
    
}

/* Pc grande */

@media screen and (min-width: 1200px) { 
   
}
</style>