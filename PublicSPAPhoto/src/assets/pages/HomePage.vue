<script>
import VueCookies from 'vue-cookies'
// import { store } from '../store.js';
import axios from 'axios';
import { router } from '../../router';
import HeaderComponent from '../../components/HeaderComponent.vue';
import FooterComponent from '../../components/FooterComponent.vue';

export default{
    data(){
      return{
		auth: false,
		user: null,
        getContact: false
	  }
    },
    components: {
      HeaderComponent,
      FooterComponent,
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
        getContactClick(){
            this.getContact = !this.getContact;
        }
    },
    created() {
        this.getUser();
	}
}
</script>

<template>

    <HeaderComponent/>

    <!-- questo diventerà lo show -->
   
    <section>
        <div class="profile text-center">
            <div class="profile-img">
                    <!-- <img :src="user.admin_info.picture" alt=""> -->
                <div class="icons">
                    <i class="fa-brands fa-facebook"></i>
                    <i class="fa-brands fa-instagram"></i>
                    <i class="fa-brands fa-linkedin"></i>
                </div>
            </div>
    
            <h3 class="mt-5">
                <!-- {{ user.name }} {{ user.surname }} -->
            </h3>
            <h6>
                <!-- Professione: <strong>{{ user.professions[0].name }}</strong> -->
            </h6>
            <p class="description">
                <!-- {{ user.admin_info.description }} -->
            </p>
    
            <a class="btn btn-info" @click="getContactClick">
                Contatti
            </a>
            <div v-if="getContact == true" class="card mt-3 px-5 py-3">
                <small>
                    <!-- Email: {{ user.email }} -->
                    <br>
                    <span>
                        Numero di telefono:
                    </span>
                    <!-- <span v-if="user.phone_number != null">
                        {{ user.phone_number }}
                    </span>
                    <span v-else>
                        -------
                    </span> -->
                </small> 
            </div>
        </div>
    </section>
    
    <section>
        <div class="container">
            <div class="instruction">
                <h2 class="text-center">
                    What to do:
                </h2>
    
                <div class="row">
                    <div class="instruction-box">
                        <h4>
                            subscribe
                        </h4>
                    </div>
                    <div class="instruction-box">
                        <h4>
                            make an appointment online by yourself
                        </h4>
                    </div>
                    <div class="instruction-box">
                        <h4>
                            i don't know
                        </h4>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <FooterComponent/>

</template>


<style lang="scss" scoped>
@use '../scss/variables.scss' as *;

.profile{
    background-color: beige;
    padding: 40px 0;
    display: flex;
    flex-wrap: wrap;
    flex-direction: column;
    justify-content: center;

    .profile-img{
        width: 200px;
        height: 200px;
        border-radius: 50%;
        background-color: blueviolet;
        position: relative;

        .icons{
            position: absolute;
            bottom: -15px;
            left: 30px;
            font-size: 1.5em;
            color: white;
            display: flex;
            
            >i{
                background-color: blue;
                border-radius: 50%;
                margin: 0 5px;
                padding: 7px;
            }
        }
        img{
            width: 100%;
            height: 100%;
            object-fit: contain;
            border-radius: 50%;
        }
    }
}

.instruction{
    padding: 30px 0;
    .row{
        >*{
            margin: 30px 20px;
        }
        .instruction-box{
            width: calc((100% / 3) - 40px);
            height: 200px;
            border-radius: 50px;
            background-color: aqua;
            text-align: center;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    }
}


/* 
MEDIA QUERY da Mobile first
*/
 
@media screen and (min-width: 320px) {
    .profile{
        .description{
            font-size: 0.8em;
            padding: 0 20px;
        }
    }

    .instruction{
    .row{
        >*{
            margin: 20px 10px;
        }
        .instruction-box{
            width: calc(100% - 20px);

        }
        } 
    }

}

@media screen and (min-width: 375px) {
    .profile{
        .description{
            padding: 0 40px;
        }
    }
    .instruction{
    .row{
        >*{
            margin: 20px 10px;
        }
        .instruction-box{
            width: calc(100% - 20px);
        }
        } 
    }
    
}



@media screen and (min-width: 425px) {
    .profile{
        .description{
            padding: 0 60px;
        }
    }
    .instruction{
    .row{
        >*{
            margin: 20px 20px;
        }
        .instruction-box{
            width: calc(100% - 40px);
        }
        } 
    }
}

@media screen and (min-width: 576px) {
    .profile{
        .description{
            padding: 0 80px;
        }
    }
    .instruction{
    .row{
        >*{
            margin: 20px 20px;
        }
        .instruction-box{
            width: calc(100% - 40px);
        }
        } 
    }
}


/* Tablet */

@media screen and (min-width: 768px){
    .profile{
        .description{
            padding: 0 120px;
            font-size: 0.9em;
        }
    }
    .instruction{
    .row{
        .instruction-box{
            width: calc((100% / 3) - 40px);

        }
        } 
    }
}


/* Desktop */
@media screen and (min-width: 992px){
    .profile{
        .description{
            padding: 0 200px;
        }
    }
}

/* Pc grande */

@media screen and (min-width: 1200px) { 
    .profile{
        .description{
            padding: 0 350px;
        }
    }
}

</style>