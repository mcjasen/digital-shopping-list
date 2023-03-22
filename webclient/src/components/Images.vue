<template>
    <div v-if="seen" 
         class="root">
        <div class="element">

            <div class="header">
                <div class="back-button"
                    @click="closeChangeImage()">
                    <img src="../assets/back_yellow.svg" 
                        width="45"
                        style="margin: 15px 0;"
                        title="Zurück"/>
                </div>
                <div class="search">
                    <input type="text"
                           name="search"
                           @keyup.enter="getImages()"
                           v-model="searchTitle"
                           placeholder="Suche">
                </div>
                <div class="image-remove">
                    <img src="../assets/cross_yellow.svg"
                        width="55"
                        style="margin: 15px 0;"
                       @click="removeImage()"
                       title="Bild entfernen"/> 
                </div>
            </div>

            <div v-if="images !== null && images.length !== 0"
                 class="results">
                <div v-for="image in images"
                     :key="image.id"
                     ref="images"
                     @click="chooseImage(image.largeImageURL)">
                    <img :src="image.largeImageURL">
                </div>
            </div>
            
            <div v-if="images !== null && images.length === 0"
                class="no-result">
                <div class="message">
                    Keine Bilder gefunden
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import PicsApi from '../PicsApi';

export default{
    name: 'TheImages',
    data(){
        return {
            images: null,
            searchTitle: null,
        }
    },
    props:{
        element: Object,
        seen: Boolean,
    },
    watch:{
        seen(){
            if(this.seen) this.getImages();
        }
    },
    methods:{
        getImages(){
            if(this.searchTitle === null){
                if(this.element.title) this.searchTitle = this.element.title;
                else if(this.element.name) this.searchTitle = this.element.name;
            }
            PicsApi.getImages(this.searchTitle)
            .then(response => {
                this.images = response.data.hits;
            })
            .catch(error => {
                this.$log.debug(error);
            });
        },
        chooseImage(image){
            this.$emit('image-selected', image);
        },
        closeChangeImage(){
            this.searchTitle = null;
            this.$emit('close');
        },
        removeImage(){
            this.$emit('image-selected', '');
        }
    }
}
</script>

<style scoped>
.root{
    z-index: 1500;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    padding: 0 5vw;
    display: flex;
    justify-content: center;
}

.element{
    align-self: start;
    padding-top: min(5vh, 30px);
    width: 100%;
    max-width: 850px;
}

.header{
    display: flex; 
    align-items: center;
    margin-bottom: 24px;
    overflow: hidden;
    flex-grow: 1;
}

.back-button{
  color: #F0D093;
  font-size: var(--fontsize-big-big);
  padding: 8px 16px 8px 8px;
}

.search{
    width: 100%;
}

.search input,
.search input:focus,
.search input:focus-visible{
    width: 100%;
    background:#F0D093;
    color: #2F4858;
    font-size: var(--fontsize-medium);
    border: 1px solid #F0D093;
    box-shadow: 0px 0px 20px 0px #E9B85D inset;
    outline: 0 solid transparent;
    border-radius: 4px;
}

.search input::placeholder{
  color: #476D85;
}

.image-remove{
    color: #F0D093;
    font-size: var(--fontsize-bigger-big);
    padding: 16px;
}

.results{
    display: grid;
    gap: 1rem;
    grid-template-columns: repeat(1, 1fr);
    width: 100%;
    max-height: 82vh;
    padding: 1.5vh 3.5vw;
    overflow: scroll;
}

@media(min-width: 500px){
    .results{
        grid-template-columns: repeat(2, 1fr);
    }
}

.results img{
    position: relative;
    width: 100%;
}

.no-result{
    width: 100%;
    display: flex;  
    justify-content: center;  
}

.no-result .message{
    color: white;
    font-size: var(--fontsize-medium);
}
</style>
