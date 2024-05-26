<script setup lang="ts">
import MovieWord from "./MovieWord.vue";
import { Post } from "../../types.ts";

defineProps<{ words: Post[] }>();
</script>

<template>
  <v-text-field hide-details="auto" label="검색할 단어를 입력해주세요." />

  <div v-for="(word, i) in words" :key="i">
    <MovieWord :word="word" />
    <v-divider />
  </div>
</template>

<style scoped></style>
