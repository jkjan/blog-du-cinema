<script setup lang="ts">
import { UserData } from "../../app/types.ts";
import { ModelRef } from "vue";
import {userAPI} from "./userAPI.ts";

const props = defineProps<{ me: boolean }>();
const userData: ModelRef<UserData> = defineModel<UserData>("userData");

const signOut = () => {
  userData.value = null;
};

let nickname: string = "nickname"

userAPI["nickname"](userData.value.username).then((response) => {
  nickname = response.data
})
</script>

<template>
  <v-btn icon="">
    <v-avatar color="info">
      <v-img
        v-if="userData && userData.profileImage"
        :src="userData.profileImage"
        :alt="`Profile Image of ${userData.username}`"
      />
      <v-icon v-else icon="mdi-account-circle" />
    </v-avatar>

    <v-menu activator="parent" rounded>
      <v-card>
        <v-card-text>
          <div v-if="userData" class="mx-auto text-center">
            <h3>{{ nickname }}</h3>
            <div class="user-action">
              <v-divider class="my-3" />
              <v-btn variant="text" rounded>작성글 보기</v-btn>
              <div v-if="me">
                <v-divider class="my-3" />
                <v-btn variant="text" rounded @click="signOut()">
                  로그아웃
                </v-btn>
              </div>
            </div>
          </div>

          <div v-else class="mx-auto text-center">
            <h3>안녕하세요!</h3>
            <div class="user-action">
              <v-divider class="my-3" />
              <v-btn variant="text" rounded to="/sign-in">로그인</v-btn>
              <v-divider class="my-3" />
              <v-btn variant="text" rounded to="/sign-up">회원 가입</v-btn>
            </div>
          </div>
        </v-card-text>
      </v-card>
    </v-menu>
  </v-btn>
</template>

<style scoped></style>
