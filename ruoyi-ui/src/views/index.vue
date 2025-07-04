<template>
  <div class="app-container home">
    <img src="../assets/images/48830_banner.png" alt="描述文字" style="width: 100%; height:100%;" />
  </div>
</template>

<script setup name="Index">
const version = ref('3.8.9')

function goTarget(url) {
  window.open(url, '__blank')
}
</script>

<style scoped lang="scss">

</style>

