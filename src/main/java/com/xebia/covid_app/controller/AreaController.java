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

import com.xebia.covid_app.models.AreaResponse;
import com.xebia.covid_app.models.UserResponse;
import com.xebia.covid_app.service.TaskMangementService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
public class AreaController {

    private static final String CLASS_NAME = TaskMangementController.class.getName();

    private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);

    @Autowired
    private TaskMangementService service;

    @PostMapping("/addArea")
    public ResponseEntity<UserResponse> area(@RequestParam(required = false, name = "file") MultipartFile file, @RequestParam String area) throws IOException {
        UserResponse response = new UserResponse();
        try {
            JsonObject jsonObject = new JsonParser().parse(area).getAsJsonObject();
            String path;
            if (file == null) {
                path = null;
            } else {
                path = "area/" + java.util.UUID.randomUUID();
                service.uploadPictureToAzure(file, path);
            }
            service.addArea(jsonObject.get("area").getAsString(), jsonObject.get("location_id").getAsString(), path);
            response.setStatus("success");
            response.setMessage("area saved");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setStatus("failure");
            response.setMessage("something went wrong");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(value = "/area", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getArea() {

        UserResponse userResponse = new UserResponse();
        UserResponse.Payload payload = userResponse.new Payload();

        LOGGER.info("inside getArea method of " + CLASS_NAME);

        try {
            List<AreaResponse> areaList = service.getAllArea();
            LOGGER.info("List of area is:" + areaList);
            userResponse.setStatus("success");
            userResponse.setMessage("successfully get the list of Area:");
            payload.setObjectList(areaList);
            userResponse.setPayload(payload);

            return ResponseEntity.status(HttpStatus.OK).body(userResponse);
        } catch (Exception e) {
            LOGGER.error("Error while getting the area:" + e);
            userResponse.setStatus("failure");
            userResponse.setMessage("error while getting area:");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
        }

    }

    @DeleteMapping(value = "delete/area/{id}")
    public ResponseEntity<UserResponse> deleteRecord(@PathVariable("id") int id) {
        UserResponse response = new UserResponse();
        LOGGER.info("inside deleteArea method of " + CLASS_NAME);
        service.deleteArea(id);

        response.setStatus("success");
        response.setMessage("Area Deleted Successfully");
        return ResponseEntity.ok().body(response);

    }
}
