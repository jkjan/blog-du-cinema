<script setup lang="ts">
import MovieInfoSidebar from "../widgets/Sidebar.vue";
import { Ref, ref, watch } from "vue";
import MovieInfoBase from "../widgets/movie_info/MovieInfoBase.vue";
import MoviePostReader from "../entities/post/MoviePostReader.vue";
import MovieDictionary from "../widgets/movie_info/MovieDictionary.vue";
import { ComponentData, Label } from "../app/types.ts";
import {HttpStatusCode} from "axios";
import {labelAPI} from "../entities/label/labelAPI.ts";

let categories: string[] = []

const labelsForCategory: {
  [category: string]: Label[];
} = {};

const componentData: Ref<ComponentData> = ref({
  category: "",
  labels: [],
  post: [],
  nowLabelIndex: 0,
});

const getCategory = async () => {
  await labelAPI.info.list().then((response) => {
    if (response.status === HttpStatusCode.Ok) {
      response.data.forEach((item: Label) => {
        const {category, labelId, labelNum, labelName} = item;

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
      categories = Object.keys(labelsForCategory)
      componentData.value.category = categories[0];
    }
  });
};

const getPost = (labelIndex: number) => {
  console.log(
    `카테고리 ${componentData.value.category} 라벨 변경 ${labelIndex}`,
  );
  console.log(componentData.value.labels[labelIndex]);
  const labelId = componentData.value.labels[labelIndex].labelId;

  labelAPI.post.get(labelId).then((response) => {
      if (response.status === HttpStatusCode.Ok)
        componentData.value.post = response.data;
      else {
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
      :categories="categories"
      v-model="componentData.category"
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
