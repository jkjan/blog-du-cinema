<script setup lang="ts">
import {Validation} from '@vuelidate/core'

const prop = defineProps<{
  checkValidation: boolean
  v$: Validation
}>()

const model = defineModel<{username: string, password: string}>()
</script>

<template>
  <form>
    <div class="d-flex">
      <v-text-field
          v-model="model.username"
          :counter="10"
          :error-messages="v$.$errors.map(e => e.$message.toString())"
          label="아이디"
          required
          @blur="v$.username.$touch"
          @input="v$.username.$touch"
      ></v-text-field>
      <v-btn v-if="checkValidation">
        중복 확인
      </v-btn>
    </div>
    <v-text-field
        v-model="model.password"
        :error-messages="v$.password.$errors.map(e => e.$message.toString())"
        label="비밀번호"
        type="password"
        required
        @blur="v$.password.$touch"
        @input="v$.password.$touch"
    ></v-text-field>
  </form>
</template>

<style scoped>

</style>