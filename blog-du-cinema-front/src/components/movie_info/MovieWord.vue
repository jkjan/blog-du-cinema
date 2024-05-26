<script setup lang="ts">
import { Post } from "../../types.ts";

defineProps<{ word: Post }>();
</script>

<template>
  <h3>{{ word.contentText }}</h3>
  <p>{{ word.title }}</p>
</template>

<style scoped></style>
