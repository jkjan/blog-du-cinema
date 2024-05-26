<script setup lang="ts">
import MoviePostReader from "./movie_forum/MoviePostReader.vue";
import post from "./movie_info/definition_dummy.ts";
</script>

<template>
  <MoviePostReader :post="[post]" />
</template>

<style scoped></style>
