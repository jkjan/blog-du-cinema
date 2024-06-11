<script setup lang="ts">
import { useVuelidate } from '@vuelidate/core'
import {maxLength, required} from '@vuelidate/validators'
import {computed, ref} from "vue";
import {userAPI} from "../entities/user/userAPI.ts";
import {HttpStatusCode} from "axios";
import UsernamePassword from "../entities/user/UsernamePassword.vue";
import {useRouter} from "vue-router";
import {userState} from "../entities/user/userState.ts";

const router = useRouter()

const userData = ref({
  username: "",
  password: ""
})

const rules = computed(() => ({
  username: { required, maxLength: maxLength(10) },
  password: { required }
}))

const v$ = useVuelidate(rules, userData)

const signUp = async (): Promise<void> => {
  const isFormCorrect = await v$.value.$validate()
  if (!isFormCorrect) {
    console.log("Error")
  }
  else {
    userAPI['sign-up'](userData.value.username, userData.value.password).then((response) => {
      if (response.status === HttpStatusCode.Created) {
        alert(response.data.message)

        userState.value.isLoggedIn = true
        userState.value.nowUserData = {
          userId: "1",
          username: userData.value.username.toString(),
          profileImage: "stub.jpg",
          jwtToken: response.headers["authorization"]
        }

        router.push("/")
      }
    }).catch((error) => {
      if (error.response.status === HttpStatusCode.Conflict) {
        alert(error.response.data.message)
      }
    })
    console.log("Done!")
  }
}
</script>

<template>
<UsernamePassword :check-validation="true" :v$="v$" v-model="userData"/>
  <v-btn @click="signUp">
    회원 가입
  </v-btn>
</template>

<style scoped>

</style>