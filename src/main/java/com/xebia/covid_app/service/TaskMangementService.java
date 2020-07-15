package com.xebia.covid_app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xebia.covid_app.entities.Frequency;
import com.xebia.covid_app.entities.Location;
import com.xebia.covid_app.models.AreaResponse;
import com.xebia.covid_app.models.CategoryResponse;
import com.xebia.covid_app.models.TaskRequest;
import com.xebia.covid_app.models.TaskResponse;

public interface TaskMangementService {

	public void createRecords(TaskRequest taskRequest,TempUsername tempUsername);

	public List<TaskResponse> getAllRecords();

	public List<CategoryResponse> getAllCategories();

	public List<Frequency> getAllFrequency();

	public List<AreaResponse> getAllArea();

	public List<Location> getAllLocation();

	void addArea(String area, String location_id, String path);

	void addCategory(String category, String location_id, String path);

	public void deleteRecord(int id);

	void uploadPictureToAzure(MultipartFile file, String uploadPath) throws IOException;

	public void deleteArea(int id);

	public void deleteCategory(int id);

	public void updateRecord(int Task_id,String comments,String path);

	void renewStatus();

	void rejectRecord(int task_id, String comment);

    List<TaskResponse> editRecord(TaskRequest taskRequest,int id);
}
