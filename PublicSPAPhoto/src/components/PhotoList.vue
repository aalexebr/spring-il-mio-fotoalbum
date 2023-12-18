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

    <main v-if="photos != null">
        <div class="container py-5">
            current page: {{ currentPage+1 }} total pages:{{ totPages }}
            <div class="row">
                <div v-for="(photo, i) in photos" :key="i"
                    class="col-3">
                    <div class="card">
                        <div class="img">
                            <img :src="photo.url">
                        </div>
                        <div>
                           title: {{ photo.title }}
                        </div>
                        <!-- <p>
                            description:
                            <br>
                            {{ photo.description }}
                        </p>
                        <div>
                            by: {{ photo.user.username }}
                        </div> -->

                        <!-- {{ i }} -->
                    </div>
                    <button @click="$emit('contact',photo.user.username)">contact artist</button>
                    <button @click="$emit('seePhoto',i)">view</button>
                </div>
            </div>
            <button @click="$emit('nextPage')">next</button>
            <button @click="$emit('prevPage')">prev</button>
        </div>
    </main>

</template>




<style lang="scss" scoped>
img{
    width: 75px;
}

</style>