<script setup lang="ts">
import { Index } from "../../types.ts";

defineProps<{ indexes: Index[] }>();
defineEmits<{ (e: "changeIndexKey", key: string): void }>();
</script>
<template>
  <v-sheet rounded="lg">
    <v-list rounded="lg">
      <v-list-item
        v-for="i in indexes"
        :key="i.key"
        :title="i.label"
        @click="$emit('changeIndexKey', i.key)"
        link
      ></v-list-item>
    </v-list>
  </v-sheet>
</template>

<style scoped></style>
