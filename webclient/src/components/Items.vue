<template>
  <div>

    <div class="header">

      <div class="justify-content-left">
        <div class="lists-back-button"
             @click="openLists()">
          <img src="../assets/back.svg"
              width="50"
             title="Zurück"/>
        </div>

        <input class="list-title"
               v-model="list.name"
               ref="title"
               @change="changeListName()"/>
      </div>

      <div class="justify-content-right">
        <div class="list-element-rename">
            <img src="../assets/pen.svg"
                width="45"
               @click="openChangeTitle()"
               title="Listennamen ändern"/>
        </div>
      </div>

    </div>

    <AddItem v-on:update-items="updateItems"
            :list="list"/>

    <div class="grid-container">
      <div v-if="items.length !== 0"
           class="grid">

          <div class="element card"
               :class="{darkerImage: item.hover}"
               v-for="(item) in items" 
               :key="item.id"
               ref="items"
               @touchstart="start(item)"
               @touchend="stop(item)"
               @touchcancel="stop(item)"
               @mouseover="item.hover = true"
               @mouseleave="item.hover = false"
               @mouseup="unassignItem(item, $event)">

            <img class="image"
                 :src=item.img 
                 alt="">
            <div v-if="item.hover"
                 class="item-options">
              <img src="../assets/pen_yellow.svg"
                  width="40"
                 ref="options"
                 @click="showItem(item)"
                 title="Artikel bearbeiten"/>
            </div>

            <div class="text">
              <div class="title">
                    {{item.title}}
              </div>
              <div v-if="item.description === '' || item.description === null"
                    class="description empty-textfield">
                    Beschreibung; Menge
              </div>
              <div v-if="item.description !== '' && item.description !== null"
                    class="description"
                    ref="description">
                    {{item.description}}
              </div>
            </div>

          </div>
      </div>

      <div v-if="items.length==0"
           class="done">
        <p>
          Alles Erledigt
          <img src="../assets/icons8-party-popper-96.png">
        </p>
      </div>

    </div>

    <Item :item="selectedItem"
          :seen="itemSeen"
          v-on:popUpStatusChanged="changeCloseItemStatus"/>
    <div v-bind:class="{darkOverlay: itemSeen}"
         @click="showItems()">
    </div>

  </div>
</template>

<script>
  import AddItem from './AddItem.vue';
  import Item from './Item.vue';
  import Api from '../Api'; 
  
  export default {
    name: 'TheItems',
    components: {
      AddItem,
      Item,
    },
    data() {
      return {
        items:[],
        newItem: null,
        error: null,
        loading: null,
        selectedItem: null,
        itemSeen: false,
        interval: false,
        count: 0,
        closeItemStatus: true,
        list_id: null,
        list: null,
      }
    },
    created(){
      this.updateItems();
    },
    watch: {
      '$route': 'updateItems'
    },
    methods:{
      openLists(){
        this.$router.push({name: 'Lists'});
      },
      start(item){
        if(!this.interval){
          this.interval = setInterval(() => {
            this.showItem(item);
            this.count++;
          }, 700);
        }
      },
      stop(item){
        if(this.count < 1) this.unassignItem(item);
        clearInterval(this.interval);
        this.interval= false;
        this.count = 0; 
      },
      showItem(item){
        this.selectedItem = item; 
        this.itemSeen = true;
      },
      showItems(){
        if(this.closeItemStatus){
          this.itemSeen = false;
          this.newItem = this.selectedItem;
          this.updateItem();
        }
      },
      changeCloseItemStatus(newStatus){
        this.closeItemStatus = newStatus; 
      },
      updateItem(){
        Api.updateItemForId(this.newItem).then(() => {
          this.updateItems();
        })
        .catch(error => {
        this.$log.debug(error);
        });
      },
      updateItems(){
        this.list_id = this.$route.params.id;
        Api.getList(this.list_id)
          .then(response => {
            if(response.data === null) this.$router.push({name: 'Lists'});
            this.list = response.data;
            Api.getAllItems(this.list_id)
              .then(response => {
                this.items = response.data;
              })
              .catch(error => {
                this.$log.debug(error)
                this.error = "Fehler beim Laden der Elemente"
              });
          })
          .catch(error => {
            this.$log.debug(error)
            this.error = "Fehler beim Laden der Elemente"
          });
      },
      unassignItem(item, event) {
        if(event !== undefined){
          if(event.target.parentElement.className !== "item-options") {
            this.newItem = item;
            this.newItem.hover = false;
            this.newItem.assigned = false;
            this.updateItem();
          }
        }
        else{
          this.newItem = item;
          this.newItem.hover = false;
          this.newItem.assigned = false;
          this.updateItem();
        }
      },
      openChangeTitle(){
        this.$refs["title"].focus();
      },
      changeListName(){
        Api.updateList(this.list)
        .catch(error => {
            this.$log.debug(error);
        });
      }
    }
  }

</script>

<style scoped>

.header{
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header .justify-content-left{
  justify-content: left;
  display: flex;
  align-items: center;
}

.header .justify-content-right{
  justify-content: right;
}

.lists-back-button{
  color: #2F4858;
  font-size: var(--fontsize-big-big);
  padding: 8px 16px 8px 8px;
}

.list-title,
.list-title:focus,
.list-title:focus-visible{
  font-size: var(--fontsize-biggest-big);
  color: #2F4858;
  font-weight: bolder;
  background: transparent;
  border: none;
  outline: none;
  width: 100%;
}

.justify-content-left{
  justify-content: left;
  align-items: center;
}

.list-element-rename{
    color: #2F4858;
    margin: 8px 18px;
    font-size: var(--fontsize-medium);
}

.grid-container {
  background: #F0D093;
  border: 0px solid grey;
  border-radius: 6px;
  box-shadow: 0px 0px 25px 0px #E9B85D inset;
}

.grid{  
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(2, 1fr);
  width: 100%;
  max-height: 75vh;
  padding: 1.5vh 3.5vw; 
  overflow: scroll;
}

@media(min-width: 400px){
  .grid{
    grid-template-columns: repeat(2, 1fr);
  }
}

@media(min-width: 600px){
  .grid{
    grid-template-columns: repeat(3, 1fr);
  }
}

@media(min-width: 950px){
  .grid{
    grid-template-columns: repeat(4, 1fr);
  }
}

@media(min-width: 1101px){
  .grid{
    width: 100%;
    max-height: 75vh;
    padding: 2.5vh 1.5vw; 
  }
}

.grid .card{
  background: #2F4858;
  border: 0px solid white;
  border-radius: 12px;
  margin: 0px;
  position: relative;
}

.grid .image{
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
  height: 13vh;
  object-fit: cover;
}

.grid .darkerImage{
  filter: brightness(0.8);
}

.grid .item-options{
  position: absolute;
  right: 0;
  top: 0;
  color: #E9B85D;
  font-size: 1.5rem;
  padding: 8px;
}

.grid .text div{
  color: white;
  margin: 0; 
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.grid .title{
  font-size: var(--fontsize-small);
  padding: 0 8px; 
}

.grid .description{
  font-size: var(--fontsize-super-super-small);
  padding: 0 8px; 
  padding-bottom: 8px;
  max-height: 2.2rem;
}

.grid .empty-textfield{
  color: transparent !important;
}

.done{
  justify-content: center;
  display: flex;
  padding: 15px;
}

.done p{
  font-size: var(--fontsize-big);
  margin: 0px;
}

.done img{
  width: 5rem;
}

.darkOverlay{
  position: absolute;
  left: 0px;
  top: 0px;
  backdrop-filter: brightness(0.3);
  width: 100%;
  height: 100%;
  z-index: 10;
}
</style>