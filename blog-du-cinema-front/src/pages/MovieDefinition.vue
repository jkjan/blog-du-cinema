<script setup lang="ts">
import MoviePostReader from "../entities/post/MoviePostReader.vue";
import { Post } from "../app/types.ts";

const post: Post[] = [
  {
    title:
      "Movie a film shown in a cinema or on television and often telling a story.",

  },
];
</script>

<template>
  <MoviePostReader :post="post" />
</template>

<style scoped></style>
