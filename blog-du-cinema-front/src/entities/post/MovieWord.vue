<script setup lang="ts">
import { Post } from "../../app/types.ts";

defineProps<{ word: Post }>();
</script>

<template>
  <h3>{{ word.title }}</h3>
  <p>{{ word.contentHtml }}</p>
</template>

<style scoped></style>
