package com.example.demo.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.model.Todo;

import com.example.demo.service.UserServer;


@RestController
@RequestMapping("/api/v1/")

public class CalendarController {
	
	
	@Autowired
	private UserServer userService;
	
	
	@GetMapping("/user")
	   public Principal user(Principal principal) {
	      return principal;
	   }
	
	
	@Bean
	public WebMvcConfigurer configurer() {
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("*");
			}
		};
	}
	

	@PostMapping(value = "/todos")
	public Todo createTodo(@RequestBody Todo todo) throws IOException, GeneralSecurityException {		
		
			
		return userService.create(todo);
	}
	
	
	
	
	@GetMapping(value = "/todos")
	public List<String> getAll() throws IOException, GeneralSecurityException{
		
			return userService.getAll();
	}
	
	
	@GetMapping(value = "/todos/{id}")
	public String getTodo(@PathVariable String id) throws IOException, GeneralSecurityException  {
		
		return userService.getByeventID(id);
	}
	
	
	@PutMapping(value = "/todos/{id}")
	public Todo updateTodo(@PathVariable String id, @RequestBody Todo todo) throws IOException, GeneralSecurityException {
		
		return userService.updateTodo(id, todo);
	}
	
	
	
	@DeleteMapping(value = "/todos/{id}")
	public String deleteTodo(@PathVariable String id) throws IOException, GeneralSecurityException {
		
		userService.delete(id);
		
	return "Deleted "+id;
	} 	
	


}
