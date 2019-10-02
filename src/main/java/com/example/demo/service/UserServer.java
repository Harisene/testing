package com.example.demo.service;

import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Testing1Application;
import com.example.demo.model.Todo;
import com.example.demo.repository.UserRepository;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@Service
public class UserServer{
	
	@Autowired
	private UserRepository userRepository;
	
	
	 private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
	    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	    private static final String TOKENS_DIRECTORY_PATH = "tokens";

	    /**
	     * Global instance of the scopes required by this quickstart.
	     * If modifying these scopes, delete your previously saved tokens/ folder.
	     */
	    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
	    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

	    /**
	     * Creates an authorized Credential object.
	     * @param HTTP_TRANSPORT The network HTTP Transport.
	     * @return An authorized Credential object.
	     * @throws IOException If the credentials.json file cannot be found.
	     */
	    
	    
	    
	    
	    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
	        // Load client secrets.
	        InputStream in = Testing1Application.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	        if (in == null) {
	            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
	        }
	        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

	        // Build flow and trigger user authorization request.
	        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
	                .setAccessType("offline")
	                .build();
	        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
	        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	    }
	    

	    
	
	public Todo create(Todo toDo) throws IOException, GeneralSecurityException {
	
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
        Event event = new Event()
        		
        	    .setSummary(toDo.getTitle())
        	    .setLocation(toDo.getLocation())
        	    .setDescription(toDo.getDetail());

        	DateTime startDateTime = new DateTime(toDo.getStartDate()+"T"+toDo.getStartTime()+"+05:30");
        	EventDateTime start = new EventDateTime()
        	    .setDateTime(startDateTime)
        	    .setTimeZone("UTC");
        	event.setStart(start);

        	DateTime endDateTime = new DateTime(toDo.getEndDate()+"T"+toDo.getEndTime()+"+05:30");
        	EventDateTime end = new EventDateTime()
        	    .setDateTime(endDateTime)
        	    .setTimeZone("UTC");
        	event.setEnd(end);

        	String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
        	event.setRecurrence(Arrays.asList(recurrence));

        	EventAttendee[] attendees = new EventAttendee[] {
        	   // new EventAttendee().setEmail("harithsenevi4@gmail.com"),
        	   
        	};
        	event.setAttendees(Arrays.asList(attendees));

        	EventReminder[] reminderOverrides = new EventReminder[] {
        	    new EventReminder().setMethod("email").setMinutes(24 * 60),
        	    new EventReminder().setMethod("popup").setMinutes(10),
        	};
        	Event.Reminders reminders = new Event.Reminders()
        	    .setUseDefault(false)
        	    .setOverrides(Arrays.asList(reminderOverrides));
        	event.setReminders(reminders);

        	String calendarId = "primary";
        	event = service.events().insert(calendarId, event).execute();
        	System.out.printf("Event created: %s\n", event.getHtmlLink()+"\n EventId : "+event.getId());
		
		toDo.setId(ObjectId.get());		
		toDo.setEventID(event.getId());
		
		return userRepository.save(toDo);		
	}
	
	
	
	
	public List<String> getAll() throws IOException, GeneralSecurityException{
		
		ArrayList<String> allTodo = new ArrayList<>();
		
		 final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	                .setApplicationName(APPLICATION_NAME)
	                .build();
	        
	        String pageToken = null;
	        do {
	          Events events = service.events().list("primary").setPageToken(pageToken).execute();
	          List<Event> items = events.getItems();
	          for (Event event : items) {
	        	String data = "Title : "+event.getSummary()+" - "+"Location : "+event.getLocation()+"\n";
	        	allTodo.add(data);
	        	System.out.println(data);
	          }
	          pageToken = events.getNextPageToken();
	        } while (pageToken != null);
		
		return allTodo;
	}
	
	
	
	
	
	public List<Todo> getById(ObjectId id) {	
	
			// Retrieve an event		
		return userRepository.findById(id);
	}
	
	public String getByeventID(String id) throws IOException, GeneralSecurityException {	
		
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
			// Retrieve an event
			Event event = service.events().get("primary", id).execute();

			
			
			String data = "Title : "+event.getSummary()+"\n"+"Description : "+event.getDescription()+"\n"+"StartDateTime :"+event.getStart().getDateTime()+"\n"+"EndDateTime : "+event.getEnd().getDateTime()+"\n"+"Location : "+event.getLocation()+"\n";
			
			System.out.println(data);
			
			
			return data;
			
			
	
}

	
	
	public Todo updateTodo(String id, Todo toDo) throws IOException, GeneralSecurityException {
		
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
		
        Event event = service.events().get("primary", id).execute();

     // Make a change
        event.setSummary(toDo.getTitle());
        event.setLocation(toDo.getLocation());
        event.setDescription(toDo.getDetail());

	DateTime startDateTime = new DateTime(toDo.getStartDate()+"T"+toDo.getStartTime()+"+05:30");
	EventDateTime start = new EventDateTime()
	    .setDateTime(startDateTime)
	    .setTimeZone("UTC");
	event.setStart(start);

	DateTime endDateTime = new DateTime(toDo.getEndDate()+"T"+toDo.getEndTime()+"+05:30");
	EventDateTime end = new EventDateTime()
	    .setDateTime(endDateTime)
	    .setTimeZone("UTC");
	event.setEnd(end);

     // Update the event
     Event updatedEvent = service.events().update("primary", event.getId(), event).execute();
     
     toDo.setEventID(id);

     System.out.println("Event updated Successfully.\n"+toDo);
	
		
		
		return toDo;
		
		
		
	}	
	
	
	
	public void delete(String id) throws IOException, GeneralSecurityException {
		
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
			
			service.events().delete("primary", id).execute();
		
		userRepository.deleteByeventID(id);
		
	}
	
		
		
}
