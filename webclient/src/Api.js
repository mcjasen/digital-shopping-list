import axios from 'axios';
  
const SERVER_URL = 'http://localhost:9000';  
  
const instance = axios.create({  
  baseURL: SERVER_URL,  
  timeout: 1000 
});  
  
export default {   
  // item   
  getAllItems: (id) => instance.get('shopping_list/item?list=' +id, {  
    transformResponse: [function (data) {  
      return data? JSON.parse(data) : data;
    }]  
  }),  

  createNewItem: (newItem, list_id) => instance.put('shopping_list/item?list=' + list_id, newItem, {
    headers:{
      'Content-Type': 'application/json',
    },
  }),  

  updateItemForId: (newItem) => instance.post('shopping_list/item?id='+newItem.id, newItem, {
    headers:{
      'Content-Type': 'application/json',
    },
  }),  

  removeItemForId: (id) => instance.delete('shopping_list/item?id='+id),

  // list
  getAllLists: () => instance.get('shopping_list/lists', {
    transformResponse: [function (data) {
      return JSON.parse(data);
    }]
  }),

  getList: (id) => instance.get('shopping_list/list?id='+id, {
    transformResponse: [function(data) {
      return JSON.parse(data);
    }]
  }),

  createNewList: (newList) => instance.post('shopping_list/list?name=' + newList.name + '&img=' + newList.img, {
    transformResponse: [function (data) {
      return JSON.parse(data);
    }]
  }),

  updateList: (newList) => instance.put('shopping_list/list?id=' +newList.id, newList),

  removeList: (id) => instance.delete('shopping_list/list?id=' +id),

}