<script setup lang="ts">
defineProps<{ categories: string[] }>();
const model = defineModel();
let activeTab: number = 0;
</script>

<template>
  <v-card>
    <v-tabs align-tabs="title" v-model="activeTab">
      <v-tab v-for="(m, i) in categories" :key="i" @click="model = m">
        {{ m }}
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
