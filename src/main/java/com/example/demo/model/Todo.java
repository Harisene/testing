package com.example.demo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="todo")
public class Todo {
	@Id
	private ObjectId id;
	private String location;
	private String title;
	private String detail;
	private String startTime;
	private String startDate;
	private String endTime;
	private String endDate;
	private String eventID;
	
	
	public Todo() {
		
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Todo(ObjectId id, String title, String detail,String startTime, String startDate,String endTime, String endDate) {
		
		this.id = id;
		this.title = title;
		this.detail = detail;
		this.startTime = startTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.endTime = endTime;
		
		
		
	}
	
	
public Todo(String title, String detail,String startTime, String startDate,String endTime, String endDate) {
		
		
		this.title = title;
		this.detail = detail;
		this.startTime = startTime;
		this.startDate = startDate;
		this.endDate = endDate;
		this.endTime = endTime;
		
		
		
		
	}
		
		public String getLocation() {
			return location;
		}
		
		public void setLocation(String location) {
			this.location = location;
		}
		
			
	public Todo(Todo todo) {
		
		this.id = todo.getId();		
		this.title = todo.getTitle();
		this.detail = todo.getDetail();
		this.startTime = todo.getStartTime();
		this.startDate = todo.getStartDate();
		this.endDate = todo.getEndDate();
		this.endTime = todo.getEndTime();
		
	}
	
	



	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	

	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	
	

}
