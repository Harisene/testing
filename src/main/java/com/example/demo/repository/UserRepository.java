package com.example.demo.repository;
 
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Todo;


@Repository
public interface UserRepository extends MongoRepository<Todo, String> {


	public List<Todo> findById(ObjectId id);
	public List<Todo> findByeventID(String id);
	public void deleteByeventID(String id);
	
}
