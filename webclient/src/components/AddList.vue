<template>
    <div>
        <div v-if="newItemButtonSeen" 
             class="new-item-button"
             @click="openNewItemInput()">
            <img src="../assets/plus.svg" class="fas fa-plus" width="28" style="margin-right:10px"/>
            <span class="title">
                Neue Liste
            </span>
        </div>
        <div v-if="newItemInputSeen"
             class="list-element">
             <div class="no-image">
             </div>
            <div class="text-and-buttons">
                <input class="list-element-title"
                       @keypress.enter="addList" 
                       placeholder="Neue Liste"
                       v-model="newList.name"
                       autofocus>
                <div class="options">
                    <div class="list-element-delete">
                        <img src="../assets/cross_yellow.svg"
                            width="45"
                        @click="cancel_addList()"/> 
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import Api from '../Api';
import PicsApi from '../PicsApi';

export default({
    name:"AddList",
    data: function() {
        return {
            newItemButtonSeen: true,
            newItemInputSeen: false,
            newList:{
                name: '',
                img: '',
            },
            imgs: null,
        }
    },
    methods:{
        openNewItemInput(){
            this.newItemButtonSeen = false;
            this.newItemInputSeen = true;
            this.$emit("new-item");
        },
        cancel_addList(){
            this.newItemButtonSeen = true;
            this.newItemInputSeen = false;
            this.$emit('new-item-finished');
        },
        addList(e){
            PicsApi.getImages(this.newList.name)
            .then(response => {
                this.imgs = response.data.hits;
                if(this.imgs.length !== 0){
                    this.newList.img = this.imgs[0].largeImageURL; 
                }
                Api.createNewList(this.newList).then(() => {
                    e.preventDefault();
                    this.$emit('update-lists');
                }).catch((error) => {
                    this.$log.debug(error);
                    this.error = "Fehler beim Hinzufügen der Liste"
                });
                this.newItemButtonSeen = true;
                this.newItemInputSeen = false;
                this.newList.name = '';
                this.newList.img = '';
                this.$emit('new-item-finished');
            })
            .catch(error => {
                this.$log.debug(error)
                this.error = "Fehler beim Laden der Elemente"
            });
        }
    }
})
</script>

<style scoped>
.new-item-button {
    display: flex;
    align-items: center;
    width: 100%;
    height: 5vh;
}

.new-item-button .title{
    font-size: var(--fontsize-smaller-big);
    color: #2F4858;
    font-weight: 600;
}

.new-item-button i{
    margin-left: 8px;
    margin-right: 12px;
    font-size: var(--fontsize-smaller-big);
    color: #2F4858;
}

.list-grid input,
.list-grid input:focus{
    width: 95%;
    font-size: var(--fontsize-big);
    color: #EDF0DA;
    font-weight: 600;
    background: transparent;
    border: none;
    outline: none;
}
</style>


