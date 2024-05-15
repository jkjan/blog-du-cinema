<script setup lang="ts">
import { Content } from "../../types.ts";

defineProps<{ word: Content }>();
</script>

<template>
  <h3>{{ word.vocab }}</h3>
  <p>{{ word.description }}</p>
</template>

<style scoped></style>
