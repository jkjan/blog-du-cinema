<script setup lang="ts">
import MovieInfoSidebar from "./MovieInfoSidebar.vue";
import { Ref, ref, watch } from "vue";

import { movieInfoCategories } from "./info_dummy.ts";
import MovieInfoBase from "./MovieInfoBase.vue";
import MoviePostReader from "../movie_forum/MoviePostReader.vue";
import MovieDictionary from "./MovieDictionary.vue";
import { ComponentData, Label } from "../../types.ts";
import axios from "axios";

const labelsForCategory: {
  [category: string]: Label[];
} = {};

const componentData: Ref<ComponentData> = ref({
  category: "",
  labels: [],
  post: [],
  nowLabelIndex: 0,
});

const getCategory = () => {
  // TODO: /info/category 에서 라벨 전체 받아오기
  info.then((response) => {
    if (response.status === 200) {
      response.data.forEach((item: Label) => {
        const { category, labelId, labelNum, labelName } = item;

        // Check if category exists in labelsForCategory
        if (!labelsForCategory[category!]) {
          labelsForCategory[category!] = [];
        }

        labelsForCategory[category!].push({
          labelId,
          labelNum,
          labelName,
        });
      });

      console.log(labelsForCategory);
      componentData.value.category = movieInfoCategories[0].name;
    }
  });
};

const getPost = (labelIndex: number) => {
  console.log(
    `카테고리 ${componentData.value.category} 라벨 변경 ${labelIndex}`,
  );
  console.log(componentData.value.labels[labelIndex]);
  const labelId = componentData.value.labels[labelIndex].labelId;

  axios
    .get("http://211.197.212.209:8080/info/post/" + labelId)
    .then((response) => {
      if (response.status === 200) {
        componentData.value.post = response.data;
      } else {
        componentData.value.post = [];
      }
    });
};

watch(
  () => componentData.value.nowLabelIndex,
  (newLabelIndex: number) => {
    if (newLabelIndex >= 0) {
      getPost(newLabelIndex);
    }
  },
);

watch(
  () => componentData.value.category,
  (newCategory: string) => {
    componentData.value.labels = labelsForCategory[newCategory];
    componentData.value.nowLabelIndex = 0;
    getPost(0);
  },
);

getCategory();
</script>

<template>
  <MovieInfoBase
    @changeCategory="(category: string) => (componentData.category = category)"
  >
    <template #sidebar v-if="componentData.labels.length > 0">
      <MovieInfoSidebar
        @changeNowIndex="
          (nowIndex: number) => (componentData.nowLabelIndex = nowIndex)
        "
        :labels="componentData.labels"
      />
    </template>

    <template #section v-if="componentData.post.length > 0">
      <MoviePostReader
        v-if="componentData.category != 'dictionary'"
        :post="componentData.post"
      />
      <MovieDictionary v-else :words="componentData.post" />
    </template>
  </MovieInfoBase>
</template>

<style scoped></style>
