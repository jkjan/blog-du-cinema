<script setup lang="ts">
import { Content } from "../../types.ts";

defineProps<{ content: Content }>();
</script>

<template>
  <p>{{ content.description }}</p>
</template>

<style scoped></style>
