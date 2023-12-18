<script>


   export default {
    data() {
      return{
       
      }
    },
    methods: {

    //     contact(i) {
    //     this.$emit('contact', i);
    // }
      
    },
    components: {
        
    },
    props:{
       photos:Array,
       currentPage:Number,
       totPages:Number
    }
    
    
  }
</script>


<template>

    <div v-if="photos == null">
        ...loading
    </div>

    <main v-if="photos != null">
        <div class="container">
            <div>
                <div class="border d-inline-block p-1 m-1">
                    current page: {{ currentPage+1 }} 
                </div>
                <div class="border d-inline-block p-1 m-1">
                    total pages:{{ totPages }}
                </div>
            </div>
            <button class="btn btn-primary mx-2" @click="$emit('prevPage')">prev</button>
            <button class="btn btn-primary mx-2" @click="$emit('nextPage')">next</button>
            
            <div class="row mt-3">
                <div v-for="(photo, i) in photos" :key="i"
                    class="col-3">
                    <div class="card">
                        <div class="img">
                            <img :src="photo.url">
                        </div>
                        <div>
                           title: {{ photo.title }}
                        </div>
                        <button class="btn btn-success" @click="$emit('seePhoto',i)">view</button>
                    </div>
                    
                </div>
            </div>
            
        </div>
    </main>

</template>




<style lang="scss" scoped>
img{
    width: 75px;
}

</style>