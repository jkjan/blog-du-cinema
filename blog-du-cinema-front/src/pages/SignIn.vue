<script setup lang="ts">
import { useVuelidate } from "@vuelidate/core";
import { maxLength, required } from "@vuelidate/validators";
import { computed, inject, Ref, ref } from "vue";
import { userAPI } from "../entities/user/userAPI.ts";
import { HttpStatusCode } from "axios";
import UsernamePassword from "../entities/user/UsernamePassword.vue";
import { useRouter } from "vue-router";
import { UserData } from "../app/types.ts";
import { RemovableRef } from "@vueuse/core";

const signInForm = ref({
  username: "",
  password: "",
});

const router = useRouter();

const rules = computed(() => ({
  username: { required, maxLength: maxLength(10) },
  password: { required },
}));

const v$ = useVuelidate(rules, signInForm);

const userData: RemovableRef<UserData> = inject("userData");

const signIn = async (): Promise<void> => {
  const isFormCorrect = await v$.value.$validate();
  if (!isFormCorrect) {
    console.log("Error");
  } else {
    userAPI["sign-in"](signInForm.value.username, signInForm.value.password, )
      .then((response) => {
        if (response.status === HttpStatusCode.Ok) {
          userData.value = {
            username: signInForm.value.username,
            profileImage: "stub.jpg",
          };

          router.back();
        }
      })
      .catch((error) => {
        alert(error.response.data.message);
      });
    console.log("Done!");
  }
};
</script>

<template>
  <UsernamePassword :check-validation="false" :v$="v$" v-model="signInForm" />
  <v-btn @click="signIn">로그인</v-btn>
</template>

<style scoped></style>
