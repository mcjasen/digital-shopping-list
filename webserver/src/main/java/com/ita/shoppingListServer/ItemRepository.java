package com.ita.shoppingListServer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	
	@Query(value = "SELECT * FROM item i " + 
				   "JOIN list l " + 
				   "ON i.list_id = l.id " + 
				   "WHERE i.title = :item_title " +
				   "AND l.id = :list_id", nativeQuery = true)
	List<Item> findByTitle(@Param("item_title") String item_title, @Param("list_id") int list_id);
	
	
	@Query(value= "SELECT * FROM item i " + 
				  "JOIN list l " +
				  "ON i.list_id = l.id " +
				  "WHERE l.id = :id "+ 
				  "AND i.assigned = true", nativeQuery = true)
	List<Item> findAllAssigned(@Param("id") int id);

	
}
