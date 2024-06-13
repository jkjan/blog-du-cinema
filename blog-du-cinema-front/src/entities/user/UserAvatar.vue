<script setup lang="ts">
import { UserData } from "../../app/types.ts";
import { userState } from "./userState.ts";

defineProps<{ userData: UserData }>();

const signOut = () => {
  alert("로그아웃하였습니다.")
  userState.value.isLoggedIn = false;
  userState.value.nowUserData = {
    userId: "",
    username: "안녕하세요!",
    profileImage: null,
    jwtToken: "",
  };
};
</script>

<template>
  <v-btn icon="">
    <v-avatar color="info">
      <v-img
        v-if="userState.isLoggedIn && userData.profileImage!"
        :src="userData.profileImage"
        :alt="`Profile Image of ${userData.username}`"
      />
      <v-icon v-else icon="mdi-account-circle" />
    </v-avatar>

    <v-menu activator="parent" rounded>
      <v-card>
        <v-card-text>
          <div class="mx-auto text-center">
            <h3 v-if="userState.isLoggedIn">{{ userData.username }}</h3>
            <h3 v-else>안녕하세요!</h3>

            <!--
            추가적인 사용자 정보 기록 (이메일 등)
            <p class="text-caption mt-1">
              {{ userData.email }}
            </p>
            -->

            <div class="user-action">
              <div v-if="!userState.isLoggedIn">
                <v-divider class="my-3" />
                <v-btn variant="text" rounded to="/sign-in"> 로그인</v-btn>
                <v-divider class="my-3" />
                <v-btn variant="text" rounded to="/sign-up"> 회원 가입</v-btn>
              </div>
              <div v-else>
                <v-divider class="my-3" />
                <v-btn variant="text" rounded> 작성글 보기</v-btn>
                <div v-if="userData.userId === userState.nowUserData.userId">
                  <v-divider class="my-3" />
                  <v-btn variant="text" rounded @click="signOut()" >
                    로그아웃
                  </v-btn>
                </div>
              </div>
            </div>
          </div>
        </v-card-text>
      </v-card>
    </v-menu>
  </v-btn>
</template>

<style scoped></style>
