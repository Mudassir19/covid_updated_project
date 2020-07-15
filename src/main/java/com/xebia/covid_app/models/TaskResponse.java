package com.xebia.covid_app.models;

import com.xebia.covid_app.entities.Status;

import java.util.Date;

public class TaskResponse {
    private int id;
    private String taskDescription;
    private int manPower;
    private String imagePath;
    private String assignToId;
    private int categoryId;
    private int areaId;
    private String locationId;
    private String frequencyId;
    private String status;
    private String comments;

    private String taskCreatedById;
    private Date taskCreationDate;

    private Date taskUpdatDate;

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getManPower() {
        return manPower;
    }

    public void setManPower(int manPower) {
        this.manPower = manPower;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAssignToId() {
        return assignToId;
    }

    public void setAssignToId(String assignToId) {
        this.assignToId = assignToId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(String frequencyId) {
        this.frequencyId = frequencyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTaskCreatedById() {
        return taskCreatedById;
    }

    public void setTaskCreatedById(String taskCreatedById) {
        this.taskCreatedById = taskCreatedById;
    }

    public Date getTaskCreationDate() {
        return taskCreationDate;
    }

    public void setTaskCreationDate(Date taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
    }

    public Date getTaskUpdatDate() {
        return taskUpdatDate;
    }

    public void setTaskUpdatDate(Date taskUpdatDate) {
        this.taskUpdatDate = taskUpdatDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
