<script setup lang="ts">
import MovieInfoSidebar from "./MovieInfoSidebar.vue";
import { Ref, ref, watch } from "vue";

import {
  contentForIndexKey,
  indexForCategory,
  movieInfoCategories,
} from "./info_dummy.ts";
import MovieInfoBase from "./MovieInfoBase.vue";
import MoviePostReader from "../movie_forum/MoviePostReader.vue";
import MovieDictionary from "./MovieDictionary.vue";
import { ComponentData } from "../../types.ts";

const componentData: Ref<ComponentData> = ref({
  category: "",
  indexes: [],
  content: [],
  nowKey: "",
});

componentData.value.category = movieInfoCategories[0].category;

watch(
  () => componentData.value.nowKey,
  (newKey: string) => {
    componentData.value.content = contentForIndexKey[newKey] ?? [];
  },
);

watch(
  () => componentData.value.category,
  (newCategory: string) => {
    componentData.value.indexes = indexForCategory[newCategory] ?? [];

    if (componentData.value.indexes?.length >= 1) {
      componentData.value.nowKey = componentData.value.indexes[0].key;
    }
  },
  { immediate: true },
);
</script>

<template>
  <MovieInfoBase
    @changeCategory="(category: string) => (componentData.category = category)"
  >
    <template #sidebar v-if="componentData.indexes.length > 0">
      <MovieInfoSidebar
        @changeIndexKey="(key: string) => (componentData.nowKey = key)"
        :indexes="componentData.indexes"
      />
    </template>

    <template #section v-if="componentData.content.length > 0">
      <MoviePostReader
        v-if="componentData.category != 'dictionary'"
        :content="componentData.content[0]"
      />
      <MovieDictionary v-else :words="componentData.content" />
    </template>
  </MovieInfoBase>
</template>

<style scoped></style>
