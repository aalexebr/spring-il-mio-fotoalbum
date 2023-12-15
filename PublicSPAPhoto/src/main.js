import { createApp } from 'vue'
import * as bootstrap from 'bootstrap'
import { router } from './router';
import App from './App.vue'

createApp(App).use(router).mount('#app')

