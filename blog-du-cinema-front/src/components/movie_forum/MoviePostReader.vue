<script setup lang="ts">
import { Post } from "../../types.ts";

defineProps<{ post: Post[] }>();
</script>

<template>
  <div v-for="(c, i) in post">
    <h3>{{ c.title }}</h3>
    {{ c.contentHtml }}
  </div>
</template>

<style scoped></style>
