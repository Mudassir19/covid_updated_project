package com.xebia.covid_app.controller;

import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xebia.covid_app.service.TempUsername;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xebia.covid_app.entities.Frequency;
import com.xebia.covid_app.entities.Location;
import com.xebia.covid_app.models.TaskRequest;
import com.xebia.covid_app.models.TaskResponse;
import com.xebia.covid_app.models.UserResponse;
import com.xebia.covid_app.service.TaskMangementService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
public class TaskMangementController {

	private static final String CLASS_NAME = TaskMangementController.class.getName();
	private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);

	@Autowired
	TempUsername tempUsername;

	@Autowired
	private TaskMangementService service;

	@GetMapping("/renewStatus")
	public void reload(){
		service.renewStatus();
	}

	@PostMapping(value = "/addTask")
	public ResponseEntity<UserResponse> createRecords(@RequestBody TaskRequest taskRequest) {

		LOGGER.info("Creating Task Controller:");
		LOGGER.info("Task RequestBody:" + taskRequest.toString());

		UserResponse response = new UserResponse();
		try {
			service.createRecords(taskRequest,tempUsername);
			response.setMessage("Task has been successfully Added");
			response.setStatus("success");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			LOGGER.error("Exception:" + e);
			response.setMessage("Getting Exception while creating the records");
			response.setStatus("Failed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

	}

	@GetMapping(value = "/allTasks", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> getAllRecords() {

		UserResponse userResponse = new UserResponse();
		UserResponse.Payload payload = userResponse.new Payload();

		LOGGER.info("inside getAllRecords method of " + CLASS_NAME);
		try {
			List<TaskResponse> taskList = service.getAllRecords();
			LOGGER.info("List of Task is:" + taskList);
			int totalTask = taskList.size();
			LOGGER.info("Total Task is:" + totalTask);

			userResponse.setStatus("success");
			userResponse.setMessage("successfully get the list of records:");
			payload.setObjectList(taskList);
			payload.setTotalTask(totalTask);
			userResponse.setPayload(payload);
			return ResponseEntity.status(HttpStatus.OK).body(userResponse);
		} catch (Exception e) {

			LOGGER.error("Error while getting the records:" + e);
			userResponse.setStatus("failure");
			userResponse.setMessage("error while getting the records:");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
		}
	}

	@PutMapping(value = "/edit/task/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> editRecords(@RequestBody TaskRequest taskRequest,@PathVariable("id") int id){
		UserResponse userResponse = new UserResponse();
		UserResponse.Payload payload = userResponse.new Payload();

		LOGGER.info("inside editRecord method of " + CLASS_NAME);
		try {
			List<TaskResponse> taskList = service.editRecord(taskRequest,id);
			LOGGER.info("Edited Record is" + taskList);
			userResponse.setStatus("success");
			userResponse.setMessage("successfully edit the records:");
			payload.setObjectList(taskList);
			userResponse.setPayload(payload);
			return ResponseEntity.status(HttpStatus.OK).body(userResponse);
		} catch (Exception e) {
			LOGGER.error("Error while edit the records:" + e);
			userResponse.setStatus("failure");
			userResponse.setMessage("error while edit the records:");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
		}
	}

	@GetMapping(value = "/frequency", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> getFrequency() {

		UserResponse userResponse = new UserResponse();
		UserResponse.Payload payload = userResponse.new Payload();

		LOGGER.info("inside getFrequency method of " + CLASS_NAME);
		List<Frequency> frequencyList;
		try {
			frequencyList = service.getAllFrequency();
			LOGGER.info("List of Frequency is:" + frequencyList);
			userResponse.setStatus("success");
			userResponse.setMessage("successfully get the list of frequency:");
			payload.setObjectList(frequencyList);
			userResponse.setPayload(payload);

			return ResponseEntity.status(HttpStatus.OK).body(userResponse);
		} catch (Exception e) {
			LOGGER.error("Error while getting the frequency:" + e);
			userResponse.setStatus("failure");
			userResponse.setMessage("error while getting frequency:");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
		}

	}

	@GetMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> getLocation() {

		UserResponse userResponse = new UserResponse();
		UserResponse.Payload payload = userResponse.new Payload();

		try {
			List<Location> locationList = service.getAllLocation();
			LOGGER.info("List of Location is:" + locationList);
			userResponse.setStatus("success");
			userResponse.setMessage("successfully get the list of Location:");
			payload.setObjectList(locationList);
			userResponse.setPayload(payload);

			return ResponseEntity.status(HttpStatus.OK).body(userResponse);
		} catch (Exception e) {
			LOGGER.error("Error while getting the locations:" + e);
			userResponse.setStatus("failure");
			userResponse.setMessage("error while getting locations:");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
		}

	}

	@DeleteMapping(value = "delete/task/{id}")
	public ResponseEntity<UserResponse> deleteRecord(@PathVariable("id") int id) {
		UserResponse response = new UserResponse();
		LOGGER.info("inside deleteRecords method of " + CLASS_NAME);
		service.deleteRecord(id);

		response.setStatus("success");
		response.setMessage("Record Deleted Successfully");
		return ResponseEntity.ok().body(response);
	}

	@PutMapping(value = "/update/task")
	public ResponseEntity<UserResponse> updateRecord(@RequestParam(required = false, name = "file") MultipartFile file, @RequestParam String task) {
		UserResponse response = new UserResponse();
		try {
			JsonObject jsonObject = new JsonParser().parse(task).getAsJsonObject();
			String path;
			if (file == null) {
				path = null;
			} else {
				path = "task/" + java.util.UUID.randomUUID();
				service.uploadPictureToAzure(file, path);
			}
			service.updateRecord(jsonObject.get("task_id").getAsInt(), jsonObject.get("comment").getAsString(), path);
			response.setStatus("success");
			response.setMessage("task status changed");
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setStatus("failure");
			response.setMessage("something went wrong");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PutMapping(value = "/reject/task")
	public ResponseEntity<UserResponse> rejectRecord(@RequestParam String task) {
		UserResponse response = new UserResponse();
		try {
			JsonObject jsonObject = new JsonParser().parse(task).getAsJsonObject();
			service.rejectRecord(jsonObject.get("task_id").getAsInt(), jsonObject.get("comment").getAsString());
			response.setStatus("success");
			response.setMessage("task status changed");
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setStatus("failure");
			response.setMessage("something went wrong");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}