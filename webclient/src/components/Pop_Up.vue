<template>
    <div v-if='seen'
         class="root">

        <b-card class="card"
                :title="popUp.title">
            <b-card-text class="message">
                {{popUp.message}}
            </b-card-text>
            <b-button class="button confirm"
                    variant="success"
                    @click="$emit('confirm')">
                Ja
            </b-button>
            <b-button class="button abort"
                    variant="danger"
                    @click="$emit('close')">
                Nein
            </b-button>
        </b-card>
        
    </div>  
</template>

<script>

export default{
    name: 'popUp',
    data(){
        return {
            title: "Löschen",
        }
    },
    props: {
        seen: Boolean,
        popUpTitle: Object,
        popUp: Object,
    },
}
</script>

<style scoped>
.card{
    position: absolute;
    left: 10vw;
    top: 30vh;
    z-index: 200;
    width: 80vw;
    background: #2F4858;
    border: 0px solid white;
    border-radius: 12px;
    padding: 8px 12px;
    margin: 0px;
}

@media(min-width: 1101px) {
    .card{
        max-width: 700px;    
        left: calc((100vw - 700px) / 2);
    }
}

/* title */
.card h4{
    color: white;
    font-size: var(--fontsize-medium);
}

.message{
    color:white;
    font-size: var(--fontsize-super-small);
}

.button{
    margin: 12px 24px 12px 0px;
    font-size: var(--fontsize-super-small);
    border-radius: 8px;
}
</style>
