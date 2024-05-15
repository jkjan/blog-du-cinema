<script setup lang="ts">
import { movieInfoCategories } from "./info_dummy.ts";

defineEmits<{ (e: "changeCategory", category: string): void }>();
</script>

<template>
  <v-card>
    <v-tabs align-tabs="title">
      <v-tab
        v-for="(m, i) in movieInfoCategories"
        :key="i"
        @click="$emit('changeCategory', m.category)"
      >
        {{ m.name }}
      </v-tab>
    </v-tabs>

    <v-row>
      <v-col cols="2">
        <slot name="sidebar"></slot>
      </v-col>
      <v-col>
        <slot name="section"></slot>
      </v-col>
    </v-row>
  </v-card>
</template>

<style scoped></style>
