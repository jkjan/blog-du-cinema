import { createApp } from 'vue'

// Vuetify
import 'vuetify/styles'
import vuetify from "./plugins/vuetify";

// Components
import App from './App.vue'

createApp(App).use(vuetify).mount('#app')
