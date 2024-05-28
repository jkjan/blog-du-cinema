<script setup lang="ts">
import { useVuelidate } from '@vuelidate/core'
import {maxLength, required} from '@vuelidate/validators'
import {computed, ref} from "vue";

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
    console.log("Done!")
  }
}
</script>

<template>
  <h3>어서오세요.</h3>

  <form>
    <div class="d-flex">
      <v-text-field
          v-model="userData.username"
          :counter="10"
          :error-messages="v$.username.$errors.map(e => e.$message)"
          label="아이디"
          required
          @blur="v$.username.$touch"
          @input="v$.username.$touch"
      ></v-text-field>
      <v-btn>
        중복 확인
      </v-btn>
    </div>
    <v-text-field
        v-model="userData.password"
        :error-messages="v$.password.$errors.map(e => e.$message)"
        label="비밀번호"
        type="password"
        required
        @blur="v$.password.$touch"
        @input="v$.password.$touch"
    ></v-text-field>

    <v-btn
        class="me-4"
        @click="signUp()"
    >
      가입하기
    </v-btn>
  </form>
</template>

<style scoped>

</style>