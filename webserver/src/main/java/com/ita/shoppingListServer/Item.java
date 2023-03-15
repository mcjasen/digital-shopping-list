package com.ita.shoppingListServer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

@Entity
public class Item {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private String title;
    private Boolean assigned = true;
    private String description; 
    private String img;
    private boolean hover; 
    @ManyToOne
    @JoinColumn(name="list_id", nullable=false)
    private List list;
   
	public Item() {
		super();
	}
    
	public Item(List list) {
		super();
		this.list = list;
	}
	
	public Item(JSONObject item, ListRepository listRepository) {
		this.id = item.getInt("id");
		this.title = getString(item, "title");
		this.assigned = item.getBoolean("assigned");
		this.description = getString(item, "description");
		this.img = getString(item, "img");
		this.hover = getBoolean(item, "hover");
		int listId = item.getInt("list");
		this.list = listRepository.findById(listId)
								  .orElse(null);
	}
	
	private boolean getBoolean(JSONObject json, String key) {
		if(!json.isNull(key)) return json.getBoolean(key);
		else return false; 
	}

	public String getString(JSONObject json, String key) {
		if(!json.isNull(key)) return json.getString(key);
		else return "";
	}

	public Item(String title, Boolean assigned, String description, String img, List list) {
		super();
		this.title = title;
		this.assigned = assigned;
		this.description = description;
		this.img = img;
		this.list = list;
	}

    public int getList() {
		return list.getId();
	}

	public void setList(List list) {
		this.list = list;
	}
	
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img; 
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getAssigned() {
		return assigned;
	}

	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}
	
	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public Item update(Item newItem) {
		newItem.id = this.id;
		return newItem;
	}
	
}