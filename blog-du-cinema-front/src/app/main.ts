import { createApp } from "vue";

// Vuetify
import vuetify from "./vuetify.ts";

// Components
import App from "./App.vue";
import { createWebHistory, createRouter } from "vue-router";
import { routes } from "./router.ts";

const router = createRouter({
  history: createWebHistory(),
  routes,
});

const app = createApp(App)

app.use(vuetify)
app.use(router)
app.mount("#app");
