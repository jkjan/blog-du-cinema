<script setup lang="ts">
import { Label } from "../../app/types.ts";

defineProps<{ labels: Label[] }>();
defineEmits<{ (e: "changeNowIndex", nowIndex: number): void }>();
</script>
<template>
  <v-sheet rounded="lg">
    <v-list rounded="lg">
      <v-list-item
        v-for="(label, i) in labels"
        :key="label.labelId"
        :title="label.labelName"
        @click="$emit('changeNowIndex', i)"
        link
      ></v-list-item>
    </v-list>
  </v-sheet>
</template>

<style scoped></style>
