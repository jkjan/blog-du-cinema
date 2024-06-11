<script setup lang="ts">
import { postAPI } from "../entities/post/postAPI.ts";
import { userState } from "../entities/user/userState.ts";

const createNewPost = () => {
  console.log(userState.value.nowUserData.jwtToken);
  postAPI["create"](
    "test",
    "test html",
    userState.value.nowUserData.jwtToken,
  ).then((response) => {
    console.log(response);
  });
};
</script>

<template>
  <!--  <h1>영화는 세상에서 가장 아름다운 사기극이다.</h1>-->
  <v-btn @click="createNewPost()">버튼</v-btn>
</template>

<style scoped></style>
