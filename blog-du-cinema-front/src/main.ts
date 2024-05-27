import { createApp } from "vue";

// Vuetify
import vuetify from "./plugins/vuetify.ts";

// Components
import App from "./App.vue";
import { createWebHistory, createRouter } from "vue-router";
import { routes } from "./plugins/router.ts";
import axios from 'axios'
import VueAxios from 'vue-axios'

const router = createRouter({
  history: createWebHistory(),
  routes,
});

const app = createApp(App)
app.use(vuetify)
app.use(router)
app.use(VueAxios, axios)
app.provide('axios', app.config.globalProperties.axios)
app.mount("#app");
