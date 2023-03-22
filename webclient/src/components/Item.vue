<!-- eslint-disable -->
<template>
    <div v-if="seen">
        <div class="root">

            <div class="card">
                
                <div v-if="item.img !== ''"
                     class="image">
                    <img class="image-item" :src="item.img">
                    <div class="icon-open-change-image"
                         @click="openImages()">
                        <img src="../assets/pen_yellow.svg"
                            width="45"
                            title="Bild ändern"/>
                    </div>
                    <a href="https://pixabay.com/"
                       target="_blank"
                       title="Bild bereitgestellt von pixabay.com">
                        <img class="logo"
                            src="https://pixabay.com/static/img/logo_square.png">
                    </a>
                </div>

                <div v-if="item.img === ''"
                     class="image no-image">
                    <div class="message">
                        Kein Bild ausgewählt
                    </div>
                    <div class="icon-open-change-image"
                         @click="openImages()">
                        <img src="../assets/pen_yellow.svg"
                            width="45"
                           title="Bild ändern"/>
                    </div>
                </div>

                <input @change="updateItem()"
                       ref="title"
                       class="title"
                       v-model="item.title"/> 

                <input class="description"
                       @blur="updateItem()"
                       placeholder="Beschreibung; Menge"
                       ref="description"
                       v-model="item.description">
            </div>

            <Images :element="item"
                    :seen="imagesSeen"
                    v-on:image-selected="selectImage"
                    v-on:close="showItem()"/>
            <div v-bind:class="{darkOverlay: imagesSeen}"
                 @click="showItem()">
            </div>

        </div>
    </div>    
</template>

<script>
/* eslint-disable */

import Api from '../Api';
import Images from './Images.vue';

export default {
    name: 'TheItem',
    components :{
        Images,
    },
    data(){
        return {
            imagesSeen: false,
            selectedImage: null,
            popUp_imageChange_seen: false,
            newItem: null,
        }
    },
    props: {
        item: Object,
        seen: Boolean,
    },
    methods:{
        updateItem(){
            Api.updateItemForId(this.item)
            .catch(error => {
                this.$log.debug(error);
            });
        },
        openImages(){
            this.imagesSeen = true;
        },
        showItem(){
            this.imagesSeen = false;
        },
        selectImage(selectedImage){
            this.item.img = selectedImage;
            this.imagesSeen = false;
        }
    }
}
</script>

<style scoped>
.root{
    width: 100%; 
    height: 100%;
    position: absolute;
    left: 0; 
    top: 0;
    display: flex;
    align-items: center;
    justify-content: center;
}


.card{
    z-index: 100;
    width: 70vw;
    max-width: 450px; 
    background: #2F4858;
    border: 0px solid white;
    border-radius: 24px;
    margin: 0px;
}

.image{
    position: relative;
}

.image-item{
    border-top-left-radius: 24px;
    border-top-right-radius: 24px;
    max-height: 30vh;
    object-fit: cover;
    width: 100%;
    filter: brightness(70%);
}

.image .icon-open-change-image{
    position: absolute;
    top: 0;
    right: 0;
    padding: 16px 28px; 
    font-size: var(--fontsize-small);
    color: #EDF0DA;
}

.image .logo{
    border-top-left-radius: 0;
    border-top-right-radius: 0;
    position: absolute;
    bottom: 0;
    right: 0;
    width: 18%;
    padding: 8px;
}

.no-image{
    height: 30vh;
    display: flex; 
    justify-content: center;
    align-items: center;
}

.no-image .message{
    color: #808080;
    font-size: var(--fontsize-small);
}

.title,
.title:focus{
    color: white;
    background: #2F4858;
    font-size: var(--fontsize-big);
    border: none;
    outline: none;
    box-shadow: 0 0 0px transparent;
    border-radius: 24px;
    padding-left: 18px;
    padding-right: 18px;
}

.description,
.description:focus{
    color: white;
    background: #2F4858;
    border: 0px solid transparent;
    box-shadow: 0 0 0px transparent;
    outline: 0;
    font-size: var(--fontsize-super-small);
    margin-bottom: 16px;
    padding-top: 0px;
    border-radius: 24px;
    padding-left: 18px;
    padding-right: 18px;
}

.dark-overlay{
  position: absolute;
  left: 0px;
  top: 0px;
  backdrop-filter: brightness(0.3);
  width: 100%;
  height: 100%;
  z-index: 110;
}
</style>