package com.ita.shoppingListServer;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@SpringBootApplication
public class ShoppingListServerApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(ShoppingListServerApplication.class, args);
	}
	
	@Bean 
	ApplicationRunner init(ListRepository listRepository, ItemRepository itemRepository) {
		List list = new List("Supermarkt", "https://cdn.pixabay.com/photo/2016/01/27/22/10/shopping-1165437_960_720.jpg");
		listRepository.save(list);
		itemRepository.save(new Item("Brot", true, "Vollkorn", "https://cdn.pixabay.com/photo/2019/05/06/14/24/bread-4183225_960_720.jpg", list));
		itemRepository.save(new Item("Wurst", true, "100 g", "https://cdn.pixabay.com/photo/2020/02/23/16/26/sausage-4873861_960_720.jpg", list));
		itemRepository.save(new Item("Käse", true, "Gouda", "https://cdn.pixabay.com/photo/2010/12/13/10/24/cheese-2785_960_720.jpg", list));
		itemRepository.save(new Item("Nudeln", true, "Spagehtti", "https://cdn.pixabay.com/photo/2010/12/13/10/00/pasta-2093_960_720.jpg", list));
		itemRepository.save(new Item("Salat", true, "", "https://cdn.pixabay.com/photo/2016/11/18/13/48/food-1834645_960_720.jpg", list));
		
		List baumarktList = new List("Baumarkt", "https://cdn.pixabay.com/photo/2019/03/29/04/35/tools-4088531_960_720.jpg");
		listRepository.save(baumarktList);
		itemRepository.save(new Item("Säge", true, "Metallsäge", "https://cdn.pixabay.com/photo/2017/04/04/17/26/hacksaw-2202309_960_720.jpg", baumarktList));
		itemRepository.save(new Item("Nägel", true, "100 Stk", "https://cdn.pixabay.com/photo/2021/12/01/19/13/nails-6838975_960_720.jpg", baumarktList));
		return null;
	}
	
    @Bean
    public FilterRegistrationBean simpleCorsFilter() {  
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
        CorsConfiguration config = new CorsConfiguration();  
        config.setAllowCredentials(true); 
        config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
        config.setAllowedMethods(Collections.singletonList("*"));  
        config.setAllowedHeaders(Collections.singletonList("*"));  
        source.registerCorsConfiguration("/**", config);  
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);  
        return bean;  
    } 
	
}
