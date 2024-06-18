import { createApp } from "vue";

// Vuetify
import vuetify from "./vuetify.ts";

// Components
import App from "./App.vue";
import { createWebHistory, createRouter } from "vue-router";
import { routes } from "./router.ts";

import VueCookies from "vue-cookies";
import { RemovableRef, useStorage } from "@vueuse/core";
import { UserData } from "./types.ts";

const router = createRouter({
  history: createWebHistory(),
  routes,
});

const app = createApp(App);

const userData: RemovableRef<UserData> = useStorage(
  "userData",
  null,
  localStorage,
  {
    serializer: {
      read: (v: string) => (v ? JSON.parse(v) : null),
      write: (v: UserData) => JSON.stringify(v),
    },
  },
);

app.provide("userData", userData);

app.use(vuetify);
app.use(router);
app.use(VueCookies, { expires: "7d" });
app.mount("#app");
