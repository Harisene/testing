package com.example.demo;


import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;



import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserServer;



@RunWith(SpringRunner.class)
@SpringBootTest()
public class Testing1ApplicationTests {

	@Autowired
	private UserServer service;
	
	@MockBean
	private UserRepository repository;
	
	
	/*
	@Test
	public void getAllTest() {
		
		when(repository.findAll()).thenReturn(Stream
				.of(new Todo(ObjectId.get(), "Meeting", "Having a urgent meeting", "8.00am", "12/09/2019")).collect(Collectors.toList()));
		
		assertEquals(1, service.getAll().size());
		
	}
	
	
	
	@Test
	public void getByIdTest() {
	
		ObjectId id = ObjectId.get();		
		
		when(repository.findById(id)).thenReturn(Stream
				.of(new Todo(id, "Meeting", "Having a urgent meeting", "8.00am", "12/09/2019")).collect(Collectors.toList()));
		
		assertEquals(1, service.getById(id).size());
	}
	
	
	@Test
	public void createTest() {
		
		//ObjectId id = ObjectId.get();	
		
		Todo todo = new Todo("Meeting", "Having a urgent meeting", "8.00am", "12/09/2019");
		
		//System.out.println(todo);
		
		when(repository.save(todo)).thenReturn(todo);

		
		assertEquals(todo.getTime(), service.create(todo));
		
	}
	

		
	
	
	@Test
	public void deteleTest() {
		
		String id = "67";
		
		service.delete(id);
		
		verify(repository, times(1)).deleteById(id);
		
	}
	
	*/
	
	
}
