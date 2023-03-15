import axios from 'axios' 
  
const SERVER_URL = 'https://pixabay.com/api/';  
const apiKey = "24333248-158a79c2e7dc39f3fb03d859d"
  
const instance = axios.create({  
  baseURL: SERVER_URL,  
  timeout: 1000  
});  
  
export default {   
  //image
  getImages: (query) => instance.get('?key=' + apiKey + '&q=' + query, {  
    transformResponse: [function (data) {  
      return data? JSON.parse(data) : data;
    }]  
  }),
  
  getImage: (query) => instance.get('?key=' + apiKey + '&q=' + query, {
    transformResponse:[function(data) {
      data = JSON.parse(data);
      var images = data.hits;
      if(images.length !== 0) return images[0].largeImageURL;
      else return null; 
   }]
  }),
}