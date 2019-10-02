package com.example.demo.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="user")
public class User {
	@Id
	private String id;	
	private Todo toDo;
	
	
	public User() {
		
	}
	

	public User(Todo toDo) {
	
		
		this.toDo = toDo;
		
		toString();
	}
	
	

	public User(String id, Todo toDo) {
		super();
		this.id = id;
		this.toDo = toDo;
	}






	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}


	public Todo getToDo() {
		return toDo;
	}



	public void setToDo(Todo toDo) {
		this.toDo = toDo;
	}



	public String toString() {

		
		
		
		return "User id : "+this.id+"TODO : "+toDo;
	}
	
	
	
	

}
