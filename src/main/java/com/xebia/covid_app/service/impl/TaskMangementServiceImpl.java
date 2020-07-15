package com.xebia.covid_app.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.xebia.covid_app.entities.*;
import com.xebia.covid_app.repository.*;
import com.xebia.covid_app.service.TempUsername;
import com.xebia.covid_app.util.DateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.xebia.covid_app.models.AreaResponse;
import com.xebia.covid_app.models.CategoryResponse;
import com.xebia.covid_app.models.TaskRequest;
import com.xebia.covid_app.models.TaskResponse;
import com.xebia.covid_app.service.TaskMangementService;

@Service
public class TaskMangementServiceImpl implements TaskMangementService {
    private static final String CLASS_NAME = TaskMangementServiceImpl.class.getName();
    private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);

    @Autowired
    TaskManagementRepository taskRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FrequencyRepository frequencyRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatusRepository statusRepository;

    public void taskGenerate(TaskRequest taskRequest, TempUsername tempUsername,int hour, int date) {
        Task task = new Task();

        task.setTaskDescription(taskRequest.getTaskDescription());
        task.setManpower(taskRequest.getManPower());

        Optional<Area> area = areaRepository.findById(taskRequest.getAreaId());
        task.setArea(area.get());

        Optional<Category> category = categoryRepository.findById(taskRequest.getCategoryId());
        task.setCategory(category.get());

        Optional<User> user1 = userRepository.findById(taskRequest.getAssignToId());
        task.setAssignTo(user1.get());

        Optional<Frequency> frequency = frequencyRepository.findById(taskRequest.getFrequencyId());
        task.setFrequency(frequency.get());

        Optional<Status> status = statusRepository.findById("S102");
        task.setStatus(status.get());

        Optional <User> user = userRepository.findByUsername(tempUsername.getUsername());
        task.setTaskCreatedBy(user.get());

        task.setTaskDate(DateManager.setTimeVariable(hour, date));
        taskRepository.save(task);
    }

    @Override
    public void createRecords(TaskRequest taskRequest,TempUsername tempUsername) {
        String frequency_id = taskRequest.getFrequencyId();
        if (frequency_id.equals("T001") || frequency_id.equals("W001")) {
            taskGenerate(taskRequest,tempUsername,9,0);
        } else if (frequency_id.equals("D001")) {
            int day = DateManager.getDay();
            int date = 0;
            for (int i = 0; i < day; i++) {
                taskGenerate(taskRequest,tempUsername,9,date);
                date++;
            }
        } else if (frequency_id.equals("2001")) {
            int hour=0;
            for (int i = 0; i < 5; i++){
                taskGenerate(taskRequest,tempUsername,9+hour,0);
                hour=hour+2;
            }
        }
    }

    @Override
    public List<TaskResponse> getAllRecords() {

        TaskResponse response = null;
        List<TaskResponse> responseList = new ArrayList<TaskResponse>();
        List<Task> taskList = taskRepository.findAll();

        for (int i = 0; i < taskList.size(); i++) {

            response = new TaskResponse();
            response.setId(taskList.get(i).getId());
            response.setTaskDescription(taskList.get(i).getTaskDescription());
            response.setManPower(taskList.get(i).getManpower());
            response.setImagePath(taskList.get(i).getImagepath());
            response.setAssignToId(taskList.get(i).getAssignTo().getId());
            response.setCategoryId(taskList.get(i).getCategory().getId());
            response.setAreaId(taskList.get(i).getArea().getId());
            response.setLocationId(taskList.get(i).getArea().getLocation().getId());
            response.setFrequencyId(taskList.get(i).getFrequency().getId());
			response.setStatus(taskList.get(i).getStatus().getStatus());
            response.setComments(taskList.get(i).getComments());
            response.setTaskCreatedById(taskList.get(i).getTaskCreatedBy().getId());
            response.setTaskCreationDate(taskList.get(i).getTaskCreationDate());
            response.setTaskUpdatDate(taskList.get(i).getTaskUpdationDate());
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        List<CategoryResponse> catResponseList = new ArrayList<CategoryResponse>();
        List<Category> catList = categoryRepository.findAll();

        String connectString = "DefaultEndpointsProtocol=https;AccountName=covidcompliance;AccountKey=Z3gE/Kb8ZToCxoRx+RNoCclJ5nrv92A2IT+zh0Um7RSJwctaffLTLkhtPBEljDL6QW3F9+BiK4Sg7Bt/5YoeXw==;EndpointSuffix=core.windows.net";
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectString)
                .buildClient();
        String containerName = "covid";
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        String imageString = null;

        for (int i = 0; i < catList.size(); i++) {
            if (catList.get(i).getImagePath() == null) {
                imageString = null;
            } else {
                BlobClient blobClient = containerClient.getBlobClient(catList.get(i).getImagePath());
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                blobClient.download(outputStream);
                imageString = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            }
            CategoryResponse response = new CategoryResponse();
            response.setCategoryId(catList.get(i).getId());
            response.setCategoryName(catList.get(i).getCategory());
            response.setImage(imageString);
            catResponseList.add(response);
        }
        return catResponseList;
    }

    @Override
    public List<Frequency> getAllFrequency() {
        List<Frequency> frequencyList = frequencyRepository.findAll();
        return frequencyList;
    }

    @Override
    public List<AreaResponse> getAllArea() {
        List<AreaResponse> areaResponseList = new ArrayList<AreaResponse>();
        List<Area> areaList = areaRepository.findAll();

        String connectString = "DefaultEndpointsProtocol=https;AccountName=covidcompliance;AccountKey=Z3gE/Kb8ZToCxoRx+RNoCclJ5nrv92A2IT+zh0Um7RSJwctaffLTLkhtPBEljDL6QW3F9+BiK4Sg7Bt/5YoeXw==;EndpointSuffix=core.windows.net";
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectString)
                .buildClient();
        String containerName = "covid";
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);

        String imageString = null;

        for (int i = 0; i < areaList.size(); i++) {
            if (areaList.get(i).getImagePath() == null) {
                imageString = null;
            } else {
                BlobClient blobClient = containerClient.getBlobClient(areaList.get(i).getImagePath());
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                blobClient.download(outputStream);
                imageString = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            }
            AreaResponse response = new AreaResponse();
            response.setAreaId(areaList.get(i).getId());
            response.setAreaName(areaList.get(i).getArea());
            response.setImage(imageString);
            areaResponseList.add(response);
        }
        return areaResponseList;
    }

    @Override
    public List<Location> getAllLocation() {
        List<Location> locationList = locationRepository.findAll();
        return locationList;
    }

    @Override
    public void addArea(String area, String location_id, String path) {
        LOGGER.debug("add area method of " + CLASS_NAME);
        Area areaobj = new Area();
        areaobj.setArea(area);
        Optional<Location> location1 = locationRepository.findById(location_id);
        areaobj.setLocation(location1.get());
        areaobj.setImagePath(path);
        areaRepository.save(areaobj);
    }

    @Override
    public void deleteRecord(int id) {

        LOGGER.info("inside deleteRecord Method of " + CLASS_NAME);
        boolean isRecordExist = taskRepository.existsById(id);

        if (isRecordExist) {
            LOGGER.info("Record is present to delete:");
            taskRepository.deleteById(id);
        } else {
            LOGGER.info("Record is not available to delete:");
        }

    }

    @Override
    public void addCategory(String category, String location_id, String path) {
        LOGGER.debug("add area method of " + CLASS_NAME);
        Category catObj = new Category();

        catObj.setCategory(category);
        catObj.setImagePath(path);

        Optional<Location> location = locationRepository.findById(location_id);
        catObj.setLocation(location.get());
        categoryRepository.save(catObj);
    }

    @Override
    public void uploadPictureToAzure(MultipartFile file, String uploadPath) throws IOException {
        String connectString = "DefaultEndpointsProtocol=https;AccountName=covidcompliance;AccountKey=Z3gE/Kb8ZToCxoRx+RNoCclJ5nrv92A2IT+zh0Um7RSJwctaffLTLkhtPBEljDL6QW3F9+BiK4Sg7Bt/5YoeXw==;EndpointSuffix=core.windows.net";
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectString)
                .buildClient();
        String container = "covid";
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(container);
        BlobClient blobClient = containerClient.getBlobClient(uploadPath);
        blobClient.upload(file.getInputStream(), 1000000);
    }

    @Override
    public void deleteArea(int id) {

        LOGGER.info("inside deleteArea Method of " + CLASS_NAME);
        boolean isRecordExist = areaRepository.existsById(id);

        if (isRecordExist) {
            LOGGER.info("Area is present to delete:");
            areaRepository.deleteById(id);
        } else {
            LOGGER.info("Area is not available to delete:");
        }
    }

    @Override
    public void deleteCategory(int id) {

        LOGGER.info("inside deleteCategory Method of " + CLASS_NAME);
        boolean isRecordExist = categoryRepository.existsById(id);

        if (isRecordExist) {
            LOGGER.info("Category is present to delete:");
            categoryRepository.deleteById(id);
        } else {
            LOGGER.info("Category is not available to delete:");
        }

    }

    @Override
    public void updateRecord(int task_id, String comments, String path) {
        Optional<Task> task = taskRepository.findById(task_id);
        task.get().setComments(comments);
        task.get().setImagepath(path);
        task.get().setStatus(statusRepository.findById("S101").get());
        taskRepository.save(task.get());
    }

    @Override
    public void renewStatus() {
        Optional<Status> statusUpcoming = statusRepository.findById("S102");
        Optional<Status> statusPending = statusRepository.findById("S103");
        List<Task> taskList = taskRepository.findAll();
        Date date = new Date();
        for (int i = 0; i <taskList.size() ; i++) {
            if(taskList.get(i).getStatus().equals(statusUpcoming.get()) && taskList.get(i).getTaskDate().compareTo(date)<0){
                taskList.get(i).setStatus(statusPending.get());
                taskRepository.save(taskList.get(i));
            }
        }
    }

    @Override
    public void rejectRecord(int task_id, String comment) {
        Optional<Task> task = taskRepository.findById(task_id);
        task.get().setComments(comment);
        task.get().setStatus(statusRepository.findById("S104").get());
        taskRepository.save(task.get());
    }

    @Override
    public List<TaskResponse> editRecord(TaskRequest taskRequest, int id) {
        LOGGER.info("inside updateRecord Method of " + CLASS_NAME);

        LOGGER.info("********ID******"+id);
        boolean isRecordExist = taskRepository.existsById(id);

        List<TaskResponse> responseList = new ArrayList<TaskResponse>();

        if (isRecordExist) {
            LOGGER.info("Record is available to Edit:");
            Task task = taskRepository.findById(id).get();
            task.setManpower(taskRequest.getManPower());
            task.setTaskDescription(taskRequest.getTaskDescription());

            Optional<User> user1 = userRepository.findById(taskRequest.getAssignToId());
            task.setAssignTo(user1.get());

            Optional<Area> area = areaRepository.findById(taskRequest.getAreaId());
            task.setArea(area.get());

            Optional<Category> category = categoryRepository.findById(taskRequest.getCategoryId());
            task.setCategory(category.get());

            Optional<Frequency> frequency = frequencyRepository.findById(taskRequest.getFrequencyId());
            task.setFrequency(frequency.get());
            Task updateRecords = taskRepository.save(task);

            TaskResponse response = new TaskResponse();

            response.setAreaId(updateRecords.getArea().getId());
            response.setAssignToId(updateRecords.getAssignTo().getId());
            response.setCategoryId(updateRecords.getCategory().getId());
            response.setComments(updateRecords.getComments());
            response.setStatus(updateRecords.getStatus().getStatus());
            response.setFrequencyId(updateRecords.getFrequency().getId());
            response.setImagePath(updateRecords.getImagepath());
            response.setManPower(updateRecords.getManpower());
            response.setTaskDescription(updateRecords.getTaskDescription());
            response.setTaskCreatedById(updateRecords.getTaskCreatedBy().getId());
            response.setTaskCreationDate(updateRecords.getTaskCreationDate());
            response.setTaskUpdatDate(updateRecords.getTaskUpdationDate());

            response.setId(updateRecords.getId());
            responseList.add(response);

            return responseList;

        } else {

            LOGGER.info("Record is not available to Edit:");
        }
        return responseList;
    }

}
