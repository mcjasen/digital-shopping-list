package com.ita.shoppingListServer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.json.JSONObject;


@Entity
public class List {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	private String name;
	private String img; 
	@OneToMany(mappedBy = "list", cascade = CascadeType.ALL)
	private java.util.List<Item> items;

	public List() {
		super();
	}
	
	public List(String name, String img) {
		super();
		this.name = name;
		this.img = img; 
	}

	public List(JSONObject newListJSON) {
		super();
		this.name = getString(newListJSON, "name");
		this.img = getString(newListJSON, "img");
	}

	private String getString(JSONObject json, String key) {
		if(json.has(key)) return json.getString(key);
		else return null;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public java.util.List<Item> getItems() {
		return items;
	}

	public List update(List newList) {
		newList.id = this.id;
		return newList;
	}
	
}
