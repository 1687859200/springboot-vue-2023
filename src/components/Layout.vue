<script setup>
import { RouterView } from 'vue-router'
import Header from './Header.vue'
import Aside from './Aside.vue'
</script>

<template>
  <div>
    <Header />
    <div style="display: flex">
      <Aside />
      <RouterView style="flex: 1" />
    </div>
  </div>
</template>

<style scoped>

</style>
