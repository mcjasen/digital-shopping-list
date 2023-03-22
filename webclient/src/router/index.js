import Lists from '../components/Lists'
import Items from '../components/Items'

import { createRouter, createWebHistory } from "vue-router"

const routes= [
    {
        path: '/',
        name: 'Lists',
        component: Lists
    },
    {
        path: '/list/:id',
        name: 'Items',
        component: Items
    }
]


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
})

export default router