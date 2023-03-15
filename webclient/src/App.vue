<template>
  <div id="app"
       class="custom-root-container">
    <div class="custom-container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>

  export default {
    name: 'app',
    components: {
    },
}
</script>

<style>
.custom-root-container{
  --fontsize-super-big: 4rem;
  --fontsize-biggest-big: 3.5rem;
  --fontsize-bigger-big: 3.1rem;
  --fontsize-big-big: 2.8rem;
  --fontsize-big: 2.5rem;
  --fontsize-smaller-big: 2.25rem;
  --fontsize-medium: 2rem;
  --fontsize-small: 1.7rem;  
  --fontsize-super-small: 1.4rem;
  --fontsize-supuer-super-small: 1.2rem;
}

.custom-root-container{
  height: 100vh;
  background: #f5deb3;
  padding: 2.5vh calc((100vw - 1000px) / 2);
}

@media(min-width: 1101px){
  .custom-root-container{
    background: #FBEEDA;
  }

  .custom-container{
    height: 100%;
    padding: 1.5vh 2.5vw;
    background: #f5deb3;
    border: 1px solid #F1D093;
    box-shadow: 0px 0px 20px 0px #ECC06F inset;
    border-radius: 8px;
  }
}

@media(max-width: 1100px) {
  .custom-root-container{
    padding: 2.5vh 5vw;
  }
}

</style>