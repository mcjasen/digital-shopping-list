<template>
  <div class="input">
    <input class="form-control"
           @keyup.enter="addItem" 
           type="text" 
           v-model="newItem.title" 
           name="title"
           placeholder="Was brauchst du?">
  </div>
</template>

<script>
import Api from '../Api.js'
import PicsApi from '../PicsApi.js'

export default {
  name: 'AddItem',
  data : function() {
    return {
      newItem: {
        title: '',
        img: '',
      },
      imgs: null,
      error: null, 
    }
  },
  props:{
    list: Object,
  },
  methods: {
    addItem(e) {
      PicsApi.getImages(this.newItem.title)
        .then(response => {
          this.imgs = response.data.hits;
          if(this.imgs.length !== 0){
            this.newItem.img = this.imgs[0].largeImageURL; 
          }
          Api.createNewItem(this.newItem, this.list.id).then(() => {
            e.preventDefault();
            this.$emit('update-items');
          }).catch((error) => {
            this.$log.debug(error);
            this.error = "Fehler beim Hinzufügen des Elements"
          });   
          this.newItem.title = '';
          this.newItem.img = '';
          })
        .catch(error => {
          this.$log.debug(error)
          this.error = "Fehler beim Laden der Elemente"
        });
    },
  }
}
</script>

<style scoped>
.input{
  padding: 1vh 0px;
}

.form-control,
.form-control:focus{
  background:#F0D093;
  color: #2F4858;
  border: 1px solid #F0D093;
  font-size: var(--fontsize-small);
  box-shadow: 0px 0px 20px 0px #E9B85D inset;
}

.form-control:focus{
  border: 1px solid #2F4858;
}

.form-control::placeholder{
  color: #476D85;
}

</style>