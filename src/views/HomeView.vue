<template>
  <div class="body">
    <div class="container">
      <div class="cloud">
        <h2>Data Clouds Rain</h2>
      </div>
    </div>
  </div>
</template>

<srcipt>

</srcipt>

<style scoped>
*
{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.body
{
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: #000;
}
.container
{
  position: relative;
  height: 400px;
  width: 100%;
  display: flex;
  justify-content: center;
}
.container .cloud
{
  position: relative;
  width: 100px;
  height: 300px;
  z-index: 100;
}
</style>