<script setup lang="ts">
import { useVuelidate } from '@vuelidate/core'
import {maxLength, required} from '@vuelidate/validators'
import {computed, ref} from "vue";
import {userAPI} from "../entities/user/userAPI.ts";
import {HttpStatusCode} from "axios";
import UsernamePassword from "../entities/user/UsernamePassword.vue";
import {userState} from "../entities/user/userState.ts";
import {useRouter} from "vue-router";

const userData = ref({
  username: "",
  password: ""
})

const router = useRouter()

const rules = computed(() => ({
  username: { required, maxLength: maxLength(10) },
  password: { required }
}))

const v$ = useVuelidate(rules, userData)

const signIn = async (): Promise<void> => {
  const isFormCorrect = await v$.value.$validate()
  if (!isFormCorrect) {
    console.log("Error")
  }
  else {
    userAPI['sign-in'](userData.value.username, userData.value.password).then((response) => {
      if (response.status === HttpStatusCode.Ok) {
        console.log("Success!")
        alert(response.data.message)
        userState.value.isLoggedIn = true
        console.log(response.headers)
        console.log(response.headers["authorization"]);

        userState.value.nowUserData = {
          userId: "1",
          username: userData.value.username.toString(),
          profileImage: "stub.jpg",
          jwtToken: response.headers["authorization"]
        }

        router.push("/")
      }
    }).catch((error) => {
        alert(error.response.data.message)
    })
    console.log("Done!")
  }
}
</script>

<template>
  <UsernamePassword :check-validation="false" :v$="v$" v-model="userData"/>
  <v-btn @click="signIn">
    로그인
  </v-btn>
</template>

<style scoped>

</style>