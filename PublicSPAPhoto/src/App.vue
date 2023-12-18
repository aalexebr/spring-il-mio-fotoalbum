<script>
//import
import PhotoList from "./components/PhotoList.vue";
import MessageForm from "./components/MessageForm.vue";
import SearchBar from "./components/SearchBar.vue";
import SinglePhoto from "./components/SinglePhoto.vue";

import axios from 'axios';

   export default {
    data() {
      return{
        currentPage:null,
        photos:null,
        totPages:null,
		contactUser:null,
		openMessageForm:false,
		searchTitle:"",
		categoriesAll:null,
		openComponent:false,
		selectedPhoto:null
      }
    },
    components: {
      PhotoList,
	  MessageForm,
	  SearchBar,
	  SinglePhoto
    },
    methods:{
      getPhotos(){
        axios.get("http://127.0.0.1:8080/api/index")
          .then(data=>{
            // console.log(data.data.content)
            // console.log(data.data.pageable)
            this.totPages = data.data.totalPages
            this.currentPage = data.data.pageable.pageNumber
            this.photos = data.data.content
            }
          )
      },
      getCategories(){
        axios.get("http://127.0.0.1:8080/api/categories")
			.then(data=>{
				// console.log(data.data)
				this.categoriesAll = data.data
			})
      },
      nextPage(){
        if(this.currentPage < this.totPages-1){
			this.currentPage++
			this.getPhotosViaPage(this.searchTitle)
			// console.log("currrent",this.currentPage++)
			return
        }
		this.currentPage=0
		this.getPhotosViaPage(this.searchTitle)
      },
	  prevPage(){
		if(this.currentPage >= 1){
			this.currentPage--
			// console.log("currrent",this.currentPage)
			this.getPhotosViaPage(this.searchTitle)
		  } 
    },
    //   getPhotosViaPage(){
	// 	const params = { page: this.currentPage };
    //     axios.get(`http://127.0.0.1:8080/api/index`,{params})
    //       .then(data=>{
    //         this.totPages = data.data.totalPages
    //         this.currentPage = data.data.pageable.pageNumber
    //         this.photos = data.data.content
    //         }
    //       )
    //   },
	  getPhotosViaPage(string){
		const params = { page: this.currentPage };
		this.searchTitle = string
		if (this.searchTitle && this.searchTitle.trim() !== "") {
			params.title = this.searchTitle.trim();
			// this.currentPage = 0
		}
        axios.get(`http://127.0.0.1:8080/api/index`,{params})
          .then(data=>{
            this.totPages = data.data.totalPages
            this.currentPage = data.data.pageable.pageNumber
            this.photos = data.data.content
            }
          )
      },
	  sendMessage(i){
		// console.log("index", i)
		this.contactUser = i
	  },
    getPhotosByCategory(){
		const params = { page: this.categories };
        axios.get(`http://127.0.0.1:8080/api/search-by-category`,{params})
          .then(data=>{
            console.log(data.data);
            }
          )

    },
	openSinglePhoto(i){
		// this.openComponent= true
		this.selectedPhoto = this.photos[i]
		console.log(this.selectedPhoto)
	},
	closeComponents(){

	}

    },
    created(){
      	this.getPhotos()
		this.getCategories()
    }
  }
</script>



<template>
	<header v-if="!openComponent">
		<SearchBar :categories="categoriesAll"
			@searchTitle="getPhotosViaPage"
		/>
	</header>
  
	<template v-if="!openComponent">
		<PhotoList :photos="photos"
				:currentPage="currentPage"
				:totPages="totPages"
				@contact="sendMessage"
				@nextPage="nextPage"
				@prevPage="prevPage"
				@seePhoto="openSinglePhoto"
			/>
	</template>
    <SinglePhoto :photo="selectedPhoto"/>
	<MessageForm :artist="contactUser"
	/>
  
</template>




<style lang="scss">
@use 'assets/scss/main.scss';




</style>
