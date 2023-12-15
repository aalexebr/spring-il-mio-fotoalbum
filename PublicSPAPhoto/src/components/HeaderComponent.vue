<script>
//import

   export default {
    data() {
      return{
        //dati
      }
    },
    methods: {
        //function
    },
    components: {
        //dichiarazione
    },
    props:{
        //utilizzo per file padre
    }
    
    
  }
</script>


<template>
    
    <header>
        <nav>
            <div class="logo">
                <a href="/">
                    Logo
                </a>
            </div>
            <div class="links">
                <ul>
                    <li>
                        <router-link :to= "{name: 'login'}">
                            Login
                        </router-link>
                    </li>
                    <li>
                        <a href="">
                            link
                        </a>
                    </li>
                    <li>
                        <a href="">
                            link
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

</template>



<style lang="scss" scoped>
@use '../assets/scss/variables.scss' as *;

header{
    height: 50px;
    background-color: #303854;

    nav{
        height: 50px;
        display: flex;
        justify-content: space-around;
        align-items: center;

        .logo a{
            text-decoration: none;

            color: white;
        }

        .links{
            ul{
                display: flex;
                margin-bottom: 0;
                >*{
                    padding-right: 40px;
                }
                li{
                    list-style-type: none;
                    a{
                        text-decoration: none;
                        color: white;
                    }
                }
            }
        }
    }
}
</style>