<script setup lang="ts">
import { useVuelidate } from "@vuelidate/core";
import { maxLength, required } from "@vuelidate/validators";
import { computed, inject, ref } from "vue";
import { userAPI } from "../entities/user/userAPI.ts";
import { HttpStatusCode } from "axios";
import UsernamePassword from "../entities/user/UsernamePassword.vue";
import { useRouter } from "vue-router";
import { setUserDataInLocalStorage } from "../entities/user/userData.ts";
import { RemovableRef } from "@vueuse/core";
import { UserData } from "../app/types.ts";

const router = useRouter();

const signUpForm = ref({
  username: "",
  password: "",
  nickname: ""
});

const rules = computed(() => ({
  username: { required, maxLength: maxLength(10) },
  password: { required, maxLength: maxLength(10) },
  nickname: { required, maxLength: maxLength(10) },
}));

const v$ = useVuelidate(rules, signUpForm);
const userData: RemovableRef<UserData> = inject("userData");

const signUp = async (): Promise<void> => {
  const isFormCorrect = await v$.value.$validate();
  if (!isFormCorrect) {
    console.log("Error");
  } else {
    userAPI["sign-up"](signUpForm.value)
      .then((response) => {
        if (response.status === HttpStatusCode.Created) {
          userData.value = {
            username: signUpForm.value.username,
            profileImage: "stub.jpg",
            nickname: signUpForm.value.nickname,
          };

          router.push("/");
        }
      })
      .catch((error) => {
        if (error.response.status === HttpStatusCode.Conflict) {
          alert(error.response.data.message);
        }
      });
    console.log("Done!");
  }
};
</script>

<template>
  <UsernamePassword :check-validation="true" :v$="v$" v-model="signUpForm" />
  <v-text-field
      v-model="signUpForm.nickname"
      :counter="10"
      :error-messages="v$.$errors.map((e) => e.$message.toString())"
      label="닉네임"
      required
      @blur="v$.nickname.$touch"
      @input="v$.nickname.$touch"
  ></v-text-field>
  <v-btn @click="signUp"> 회원 가입 </v-btn>
</template>

<style scoped></style>
