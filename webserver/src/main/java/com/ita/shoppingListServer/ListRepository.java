package com.ita.shoppingListServer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ListRepository extends JpaRepository<List, Integer>{
			
}