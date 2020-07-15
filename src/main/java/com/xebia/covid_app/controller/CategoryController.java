package com.xebia.covid_app.controller;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xebia.covid_app.models.CategoryResponse;
import com.xebia.covid_app.models.UserResponse;
import com.xebia.covid_app.service.TaskMangementService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
public class CategoryController {

    private static final String CLASS_NAME = TaskMangementController.class.getName();
    private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);

    @Autowired
    private TaskMangementService service;

    @PostMapping("/addCategory")
    public ResponseEntity<UserResponse> area(@RequestParam(required = false, name = "file") MultipartFile file, @RequestParam String category) throws IOException {
        UserResponse response = new UserResponse();
        try {
            JsonObject jsonObject = new JsonParser().parse(category).getAsJsonObject();
            String path;
            if (file == null) {
                path = null;
            } else {
                path = "category/" + java.util.UUID.randomUUID();
                service.uploadPictureToAzure(file, path);
            }
            service.addCategory(jsonObject.get("category").getAsString(), jsonObject.get("location_id").getAsString(), path);
            response.setStatus("success");
            response.setMessage("category saved");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setStatus("failure");
            response.setMessage("something went wrong");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getCategory() {

        UserResponse userResponse = new UserResponse();
        UserResponse.Payload payload = userResponse.new Payload();

        LOGGER.info("inside getCategory method of: " + CLASS_NAME);

        try {
            List<CategoryResponse> categoryList = service.getAllCategories();
            LOGGER.info("List of Category is:" + categoryList);
            userResponse.setStatus("success");
            userResponse.setMessage("successfully get the list of category:");
            payload.setObjectList(categoryList);
            userResponse.setPayload(payload);

            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } catch (Exception e) {
            LOGGER.error("Error while getting the category:" + e);
            userResponse.setStatus("failure");
            userResponse.setMessage("error while getting category:");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
        }

    }

    @DeleteMapping(value = "delete/category/{id}")
    public ResponseEntity<UserResponse> deleteCategory(@PathVariable("id") int id) {
        UserResponse response = new UserResponse();
        LOGGER.info("inside deleteCategory method of " + CLASS_NAME);
        service.deleteCategory(id);

        response.setStatus("success");
        response.setMessage("Category Deleted Successfully");
        return ResponseEntity.ok().body(response);

    }

}