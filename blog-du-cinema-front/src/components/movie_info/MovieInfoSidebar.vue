<script setup lang="ts">
import { Label } from "../../types.ts";

defineProps<{ labels: Label[] }>();
defineEmits<{ (e: "changeIndexKey", key: string): void }>();
</script>
<template>
  <v-sheet rounded="lg">
    <v-list rounded="lg">
      <v-list-item
        v-for="(label, i) in labels"
        :key="label.labelId"
        :title="label.labelName"
        @click="$emit('changeIndexKey', i)"
        link
      ></v-list-item>
    </v-list>
  </v-sheet>
</template>

<style scoped></style>
