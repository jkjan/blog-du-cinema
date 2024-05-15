<script setup lang="ts">
import MoviePostReader from "./movie_forum/MoviePostReader.vue";
import content from "./movie_info/definition_dummy.ts";
</script>

<template>
  <MoviePostReader :content="content" />
</template>

<style scoped></style>
