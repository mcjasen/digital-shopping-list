package com.ita.shoppingListServer;


import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/shopping_list")
public class MainController {
	
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ListRepository listRepository;
	
	@GetMapping(path="/item")
	public @ResponseBody Iterable<Item> getAllItems(@RequestParam("list") int id){
		return itemRepository.findAllAssigned(id);
	}
	
	@PutMapping(path="/item")
	public @ResponseBody String createNewItem(@RequestParam("list") int listId, @RequestBody String newItemString){
		JSONObject newItemJSON = new JSONObject(newItemString);
		java.util.List<Item> items = itemRepository.findByTitle(newItemJSON.getString("title"), listId); 
		if(items.isEmpty()) {
			List list = listRepository.findById(listId)
							     	  .orElse(null);
			if(list == null) return "List not found";
			Item item = new Item(list);
			item.setTitle(newItemJSON.getString("title"));
			item.setImg(newItemJSON.getString("img"));
			itemRepository.save(item);
			return "Item saved";
		} else {
			if(items.size() > 1) System.err.println("Item exists more than one");
			Item item = items.get(0);
			item.setAssigned(true);
			itemRepository.save(item);
			return "Item assigned";
		}
	}
	
	@PostMapping(path="/item")
	public @ResponseBody String updateItemForId(@RequestParam int id, @RequestBody String newItemString) {
		Item item = itemRepository.findById(id)
								  .orElse(null);
		JSONObject newItemJSON = new JSONObject(newItemString);
		Item newItem = new Item(newItemJSON, listRepository);
		newItem = item.update(newItem);
		itemRepository.save(newItem);
		return "Item updated";
	}
	
	@GetMapping(path="/lists")
	public @ResponseBody Iterable<List> getAllLists(){
		return listRepository.findAll();
	}
	
	@GetMapping(path="/list")
	public @ResponseBody Optional<List> getList(@RequestParam int id) {
		return listRepository.findById(id);
	}
	
	@PostMapping(path="/list")
	public @ResponseBody String createNewList(@RequestParam("name") String name, @RequestParam(defaultValue = "") String img) {
		List list = new List(name, img);
		listRepository.save(list);
		return "List saved";
	}
	
	@PutMapping(path="/list")
	public @ResponseBody String updateList(@RequestParam("id") int id, @RequestBody String newListString) {
		List list = listRepository.findById(id)
								  .orElse(null);
		JSONObject newListJSON = new JSONObject(newListString);
		List newList = new List(newListJSON);
		newList = list.update(newList);
		listRepository.save(newList);
		return "List updated"; 
	}
		
	@DeleteMapping(path="/list")	
	public @ResponseBody String deleteList(@RequestParam("id") int id) {
		List list = listRepository.findById(id)
								  .orElse(null);
		if(list == null) return "List not found";
		listRepository.deleteById(list.getId());
		return "List deleted";
	}
	
}