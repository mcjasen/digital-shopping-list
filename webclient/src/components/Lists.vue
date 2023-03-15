<template>
    <div class="root-app">

        <p class="page-title">Listen</p>

        <div v-if="lists.length!==0"
             class="list-grid">
            <div v-for="(list) in lists" 
                 :key="list.id"
                 ref="lists"
                 class="list-element">

                <div v-if="list.img !== '' && list.img !== null"
                     class="image">
                    <div class="background-image">
                        <img :src="list.img"
                             @click="openList(list)">
                    </div>
                    <a href="https://pixabay.com/"
                        target="_blank"
                        title="Bild bereitgestellt von pixabay.com">
                        <img class="logo"
                                src="https://pixabay.com/static/img/logo_square.png"
                                alt="Pixabay">
                    </a>
                </div>

                <div v-if="list.img === '' || list.img === null"
                     class="no-image"
                     @click.self="openList(list)">
                </div>

                <div class="text-and-buttons">
                    <div class="list-element-title"
                         ref="input"
                         @click.self="openList(list)">
                        {{list.name}}
                    </div>
                    <div class="options">
                        <div class="list-element-rename">
                            <i class="fas fa-pen"
                               @click="openChangeImage(list)"
                               title="Bild ändern"></i>
                        </div>
                        <div class="list-element-delete">
                            <i class="fas fa-times"
                               @click="deleteList_openPopUp(list)"
                               title="Liste löschen"></i> 
                        </div>
                    </div>
                </div>

            </div>

            <AddList v-on:update-lists="updateLists"/>

        </div>

        <div v-if="lists.length === 0"
             class="list-grid">
            <div v-if="emptyListSeen"
                 class="empty">
                <p>Keine Liste bisher angelegt</p>
            </div>

            <AddList v-on:update-lists="updateLists"
                     v-on:new-item="disableEmptyList"
                     v-on:new-item-finished="ableEmptyList"/>
        </div>

        <PopUp :seen="popUpSeen"
               :popUp="popUpConfig"
               v-on:close="deleteList_closePopUp"
               v-on:confirm="deleteList"/>    
        <div v-bind:class="{darkOverlay: popUpSeen}"
             @click="deleteList_closePopUp">
        </div>  

        <Images :element="selectedList"
                :seen="imagesSeen"
                v-on:image-selected="selectImage"
                v-on:close="closeChangeImage()"/>
        <div v-bind:class="{darkOverlay: imagesSeen}"
             @click="closeChangeImage()">
        </div>  
        
    </div>
</template>

<script>
    import AddList from './AddList.vue';
    import Api from '../Api'; 
    import PopUp from "./Pop_Up.vue";
    import Images from "./Images.vue";

export default {
    name: 'Lists',
    components: {
        AddList,
        PopUp,
        Images,
    },
    data() {
        return {
            lists: [],
            newList: null, 
            selectedList: null,
            listsSeen: true,
            itemsSeen: false,
            popUpSeen: false,
            popUpConfig: {
                title: "Löschen",
                message: null,
            },
            emptyListSeen: true,
            imagesSeen: false,
            interval: null,
            count: 0,
        }
    },
    mounted() {
        Api.getAllLists()
            .then(response => {
                this.lists = response.data
            })
            .catch(error => {
                this.$log.debug(error)
                this.error = "Fehler beim Laden der Elemente"
            })
                .finally(() => this.loading = false)
        },
    methods:{
        handleClickOnItem(event, element, list){
            if(event.target != element){
                event.stopPropagation();
                return;
            }
            this.openList(list);
        },
        openList(list){
            this.$router.push({name:'Items', params:{id: list.id}})
        },
        updateLists(){
            Api.getAllLists()
            .then(response => {
                this.lists = response.data
            })
            .catch(error => {
                this.$log.debug(error)
                this.error = "Fehler beim Laden der Elemente"
            })
            .finally(() => this.loading = false)
        },
        updateName(list) {
            this.newList = list;
            this.updateLists();
        },
        updateList(){
            Api.updateList(this.newList).then(() => {
                this.updateLists();
            }).catch(error => {
                this.$log.debug(error);
            });
        },
        deleteList(){
            this.deleteList_closePopUp();
            Api.removeList(this.selectedList.id).then(() => {
                this.updateLists();
            }).catch(error => {
                this.$log.debug(error);
            });
        },
        openLists(){
            this.itemsSeen=false;
            this.listsSeen=true;
        },
        deleteList_openPopUp(list){
            this.popUpConfig.message =  "Wollen Sie die Liste " + list.name + " wirklich löschen?"
            this.selectedList = list;
            this.popUpSeen = true;
        },
        deleteList_closePopUp(){
            this.popUpSeen = false;
        },
        ableEmptyList(){
            this.emptyListSeen = true;
        },
        disableEmptyList(){
            this.emptyListSeen = false;
        },
        openChangeImage(list){
            this.selectedList = list;
            this.imagesSeen = true;
        },
        closeChangeImage(){
            this.imagesSeen = false;
        },
        selectImage(selectedImage){
            this.newList = this.selectedList;
            this.newList.img = selectedImage;
            this.imagesSeen = false;
            this.updateList(); 
        },
    }
}

</script>

<style>
.root-app{
    max-height: 80vh;
    padding: 10px; 
}

.page-title{
    font-size: var(--fontsize-super-big);
    color: #2F4858;
    font-weight: bolder;
}

.empty{
    margin: 20px;
    margin-left: 8px
}

.empty p{
    font-size: var(--fontsize-big);
    font-weight: 500;
    margin: 0px;
}

.list-grid{
    display: grid;
    gap: 1rem;
    grid-template-columns: 100%;
    max-height: 80vh;
    overflow-y: scroll;
    overflow-x: hidden;
    padding-right: 4px; 
}

.list-element{
    position: relative;
    text-align: center;
}

.list-grid .list-element .image img{
    width: 100%;
    height: 15vh;
    object-fit: cover;
    filter: brightness(70%);
    border-radius: 12px;
    background: #2F4858;
}

.list-grid .list-element .image .background-image img{
    min-height: 140px;
}

@media(min-width:1101px){
    .list-grid .list-element .image img{
        height: 10vh; 
    }

    .list-grid .list-element .image .background-image img{
        min-height: 120px;
    }   
}

.list-grid .list-element .image{
    position: relative;
}

.list-grid .list-element .image .logo{
    border-radius: 0;
    border: 0px solid transparent;
    position: absolute;
    bottom: 0;
    right: 0;
    max-width: 55px;
    min-width: 50px;
    width: 10%;
    height: auto;
    margin: 8px;
    filter: brightness(0.9);
}

@media(min-width:1101px){
    .list-grid .list-element .image .logo{
        max-width: 45px;
        min-width: 40px;
    }
}

.list-grid .list-element .no-image{
    width: 100%;
    height: 15vh;
    background: #2F4858;
    border-radius: 12px;
}

@media(min-width:1101px){
    .list-grid .list-element .no-image{
        height: 10vh; 
        min-height: 120px;
    }
}


.list-grid .list-element .text-and-buttons{
    position: absolute;
    top: 0px;
    left: 0px;
    z-index: 100;
    padding: 0 16px;
    display: grid;
    grid-template-columns: auto min-content;
    gap: 8px;
    align-items: center;
    width: 100%;
    color: #EDF0DA;
}

.list-grid .list-element-title{
    color: #EDF0DA;
    font-size: var(--fontsize-big);
    font-weight: 600;
    text-align: left;
    max-height: 3.5rem; 
}

.list-grid .options{
    display: flex;
    align-items: center;
    gap: 16px;
}

.list-grid .list-element-options{
    display: flex;
    align-items: center;
    color: #EDF0DA;
}

.list-grid .list-element-options div{
    margin: 1vh .5vw;
}

.list-grid .list-element-rename{
    font-size: var(--fontsize-small);
}

.list-grid .list-element-delete{
    font-size: var(--fontsize-big);
}

@media (max-width: 370px) { 
    .list-grid .list-element .text-and-buttons{
        grid-template-columns: 1fr;
        grid-template-rows: repeat(2, 1fr);
    }
 }

.darkOverlay{
    position: absolute;
    left: 0px;
    top: 0px;
    backdrop-filter: brightness(0.15);
    width: 100%;
    height: 100%;
    z-index: 110;
}
</style>