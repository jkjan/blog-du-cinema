<script setup lang="ts">
import UserAvatar from "../../entities/user/UserAvatar.vue";
import { inject } from "vue";
import { UserData } from "../../app/types.ts";
import { RemovableRef } from "@vueuse/core";

const menu = [
  { name: "영화란?", path: "/what-is-movie" },
  { name: "영화 정보", path: "/info" },
  { name: "게시판", path: "/forum" },
];

const userData: RemovableRef<UserData> = inject("userData");
</script>

<template>
  <v-app-bar>
    <v-container
      id="header"
      class="mx-auto my-auto d-flex align-center justify-center"
    >
      <v-app-bar-title id="blog-title" @click="$router.push('/')"
        >BLOG DU CINÉMA</v-app-bar-title
      >
      <v-btn v-for="(m, i) in menu" :key="i" :to="m.path">
        {{ m.name }}
      </v-btn>

      <UserAvatar v-model:userData="userData" :me="true" />
    </v-container>
  </v-app-bar>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Bodoni+Moda:opsz,wght@6..96,600&display=swap");

#blog-title {
  font-family: "Bodoni Moda", serif;
  font-optical-sizing: auto;
  font-style: normal;
  font-weight: 600;
  width: auto;
}

#header {
  height: auto;
}
</style>
