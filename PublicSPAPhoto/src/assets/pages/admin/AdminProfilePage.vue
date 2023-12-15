<script>

import VueCookies from 'vue-cookies';
// import { store } from '../store.js';
import axios from 'axios';
import { router } from '../../../router';
import AsideComp from '../../../components/AsideAdminComp.vue';
import PicModalComp from '../../../components/admin/ChangeProfilepicModal.vue';
axios.defaults.withCredentials = true;

export default{
    data(){
      return{
		// store,
        
        loadUser : false,
		user: null,
        changeImage: false,
        formData:{
            name: '',
            surname: '',
            phone_number: '',
            description: '',
            address: '',
        },
        formData2:{
            file: null
        },
        openProfilePicModal:false,
    
	  }
    },
    components: {
		AsideComp,
        PicModalComp,
        
    }, 
    methods:{
        getUser(){
            this.loadUser = true;
			let token = VueCookies.get('jwt')
			axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
			axios.get('http://127.0.0.1:8000/api/admin/user')
				.then(res=>{
					this.user = res.data.user
                    console.log(res.data.user)
                    this.loadUser = false;
				})
				.catch(err=>{
					console.log(err)
				})
		},
        updateUser(){
            let token = VueCookies.get('jwt')
            // console.log("sent data",this.updateData)
			axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
			axios.post('http://127.0.0.1:8000/api/admin/edit-profile',this.formData , {
                    header: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
            .then(res=>{
                console.log(res)
                this.getUser()
            })
            .catch(err => {
                    console.log(err.response.data);
                })
        },

        openChangeProfilePicModal(){
            this.openProfilePicModal = true
        },
        closePicModal(){
            this.openProfilePicModal = false
        }
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
		<div class="dashboard-inside position-relative">
            <template v-if="loadUser">
                ........loading
            </template>

            <div v-if="loadUser==false" class="profile-page">

                <div class="layover">
                    
                    <div class="profile-row">
                        <!-- card profile -->
                        <div class="profile-col">
                            <div class="my-card">
                                <div class="img-card">
                                    <img v-if="user.admin_info.picture == null" src="../../../assets/img/stockImage.png" alt="">
                                    <img v-else :src="`http://127.0.0.1:8000/storage/${user.admin_info.picture}`" alt="">
                                    <div class="edit-profile cursor-pointer" @click="openChangeProfilePicModal">
                                        <i class="fa-solid fa-pencil"></i>
                                    </div>
                                </div>
                                <PicModalComp v-if="openProfilePicModal"
                                    @close="closePicModal"
                                    @recharge="getUser"
                                    class="position-absolute top-50 start-50 translate-middle"/>

                                <!-- ❕qui link per edit del profilo -->

                                <div class="description">
                                    <div class="d-block d-sm-flex align-items-sm-center">
                                        <strong>
                                            Nome e Cognome:
                                        </strong>
                                        <p>
                                            {{ user.name }} {{ user.surname }}
                                        </p>
                                    </div>
                                    <hr>
                                    <div class="d-block d-sm-flex align-items-sm-center">
                                        <strong>
                                            Email:
                                        </strong>
                                        <p>
                                            {{ user.email }}
                                        </p>
                                    </div>
                                    <hr>
                                    <div class="d-block d-sm-flex align-items-sm-center">
                                        <strong>
                                            Numero di telefono:
                                        </strong>
                                        <p v-if="user.phone_number != null">
                                            {{ user.phone_number }}
                                        </p>
                                        <p v-else>
                                            aggiungi il tuo numero
                                        </p>
                                    </div>
                                    <hr>
                                    <div class="d-block d-sm-flex align-items-sm-center">
                                        <strong>
                                            Indirizzo:
                                        </strong>
                                        <p v-if="user.admin_info.address != null">
                                            {{ user.admin_info.address }}
                                        </p>
                                        <p v-else>
                                            aggiungi il tuo indirizzo
                                        </p>
                                    </div>
                                    <hr>
                                    <div class="d-block d-sm-flex align-items-sm-center">
                                        <strong>
                                            Descrizione:
                                        </strong>
                                        <p v-if="user.admin_info.description != null">
                                            {{ user.admin_info.description }}
                                        </p>
                                        <p v-else>
                                            aggiungi la descrizione
                                        </p>
                                    </div>
                                    <hr>
                                </div>
                            </div>
                            <div class="text-center ">
                                <a href="" class="btn btn-success mt-3 mb-5" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight"> 
                                    Modifica profilo
                                </a>
                            </div>

                            <!-- off canvas per edit del profilo -->
                            <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
                                <div class="offcanvas-header">
                                    <h6 class="offcanvas-title" id="offcanvasRightLabel">
                                        Modifica il profilo
                                    </h6>
                                    <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                                </div>
                                <!-- interno, imput -->
                                <div class="offcanvas-body">
                                    <form action="/admin-profile" method="post" @submit.prevent="updateUser()">

                                        <!-- name -->
                                        <div class="imput-group mb-3">
                                            <label for="name">Nome</label>
                                            <input type="text" class="form-control" id="name" name="name" :placeholder="user.name" v-model="formData.name" >
                                        </div>

                                        <!-- surname -->
                                        <div class="imput-group mb-3">
                                            <label for="surname">Cognome</label>
                                            <input type="text" class="form-control" id="surname" name="surname" :placeholder="user.surname" v-model="formData.surname" >
                                        </div>

                                        <!-- phone_number -->
                                        <div class="imput-group mb-3">
                                            <label for="phone_number">Numero di telefono</label>
                                            <input type="tel" class="form-control" id="phone_number" name="phone_number" :placeholder="(user.phone_number) ?? 'Inserisci il numero'" v-model="formData.phone_number">
                                        </div>

                                        <!-- address -->
                                        <div class="imput-group mb-3">
                                            <label for="address">Indirizzo</label>
                                            <input type="text" class="form-control" id="address" name="address" :placeholder="(user.admin_info.address) ?? 'Inserisci l\'indirizzo'" v-model="formData.address">
                                        </div>

                                        <!-- description -->
                                        <div class="imput-group mb-3">
                                            <label for="description">Description</label>
                                            <textarea class="form-control" :placeholder="(user.admin_info.description) ?? 'Inserisci una descrizione'" id="description" name="description" v-model="formData.description"></textarea>
                                        </div>


                                        <!-- button submit -->
                                        <button type="submit">
                                            <a >
                                                Modifica
                                            </a>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
		</div>
    </div>
</template>

<style lang="scss" scoped>
@use '../../scss/variables.scss' as *;
@use '../../scss/utils.scss' as *;

.image-box{
    width: 100px;
    height: 100px;
    border: 1px solid black;
    img{
        width: 100%;
        height: 100%;
    }
}
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

		.home-page{
			margin-top: 30px;
			text-align: center;
		}
    }
    
}


@media screen and (min-width: 320px) {
    .profile-page{
    //background-image: url('../assets/img/wave-dash-sfondo.png');
    background-position: top;
    background-size: cover;
    background-repeat: no-repeat;
    height: 100vh;
    //width: 300px;

    .layover{
        width: 80%;
        margin-top: 30px;
        margin-bottom: 30px;
        // background-color: rgba($color: #000000, $alpha: 0.2);

        .profile-row{
            //margin: 50px;
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            .profile-col{
                margin-top: 50px;
                margin-bottom: 50px;
                margin-left: 10px;
                margin-right: 10px;
                width: 100%;
                //background-color: white;
                
                .my-card{
                    background-color: rgba($color: #f8e9e9, $alpha: 0.2);
                    border-radius: 10px;
                    //min-height: 100%;
                    font-size: 0.8em;
                    .img-card{
                        width: 80%;
                        height: 250px;
                        padding-top: 20px;
                        padding-bottom: 20px;
                        position: relative;
                        img{
                            border-radius: 20px;
                            width: 100%;
                            height: 100%;
                            object-fit: cover;
                            object-position: center;
                        }
                        .edit-profile{
    
                            position: absolute;
                            top: 25px;
                            right: 10px;
                            border: 1px solid white;
                            border-radius: 10px;
                            padding: 5px;
                        }
                    }
                    .description{
                        margin-top: 20px;
                        text-align: center;
                        div{

                            >p{
                                margin-bottom: 0rem !important;
                            }
                        }
                    }
                }
            }
        }
    }
    }
}


       
@media screen and (min-width: 375px) {

}


    
@media screen and (min-width: 425px) {
    
}

@media screen and (min-width: 576px) {
    
    .layover{
        .profile-row{
            .profile-col{
                .my-card{
                    .description{
                        margin-top: 20px;
                        text-align:start;
                        div{

                            >p{
                                margin-bottom: 0rem !important;
                            }
                        }
                    }
                }
            }
        }
    }
}


/* Tablet */

@media screen and (min-width: 768px){
    .profile-page{

    .layover{

        .profile-row{
            margin: 20px;
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            .profile-col{
                margin-top: 50px;
                //background-color: white;
                .my-card{
                    display: flex;
                    justify-content: center;

                    .img-card{
                        width: 30%;
                        height: 300px;
                        padding-top: 20px;
                        padding-bottom: 20px;
                        img{
                            border-radius: 20px;
                            width: 100%;
                            height: 100%;
                            object-fit: cover;
                            object-position: center;
                        }
                    }

                    .description{
                        width: 50%;

                        div{
                            >*{
                                padding-left: 10px;
                            }
                        }
                    }
                }


            }
        }
    }
    }
}


/* Desktop */
@media screen and (min-width: 992px){
    
}

/* Pc grande */

@media screen and (min-width: 1200px) { 
   
}
</style>