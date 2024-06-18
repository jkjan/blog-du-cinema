<script setup lang="ts">
import Header from "../widgets/global/Header.vue";
import Main from "../widgets/global/Main.vue";
import Footer from "../widgets/global/Footer.vue";

//
// watch(
//   () => userData.value,
//   (newUserData: UserData) => {
//     setUserDataInLocalStorage(newUserData);
//   },
// );
</script>

<template>
  <v-app>
    <Header />
    <Main />
    <Footer />
  </v-app>
</template>

<style scoped></style>
