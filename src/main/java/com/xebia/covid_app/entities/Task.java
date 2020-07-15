package com.xebia.covid_app.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String taskDescription;
    @ManyToOne
    private Area area;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Frequency frequency;
    @ManyToOne
    private Status status;
    @ManyToOne
    private User taskCreatedBy;
    @ManyToOne
    private User taskUpdatedBy;
    @ManyToOne
    private User assignTo;
    private Date taskDate;
    @CreationTimestamp
    private Date taskCreationDate;
    @UpdateTimestamp
    private Date taskUpdationDate;
    private int manpower;
    private String imagepath;
    private String comments;

    public User getTaskUpdatedBy() {
        return taskUpdatedBy;
    }

    public void setTaskUpdatedBy(User taskUpdatedBy) {
        this.taskUpdatedBy = taskUpdatedBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }


    public User getTaskCreatedBy() {
        return taskCreatedBy;
    }

    public void setTaskCreatedBy(User taskCreatedBy) {
        this.taskCreatedBy = taskCreatedBy;
    }

    public User getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(User assignTo) {
        this.assignTo = assignTo;
    }

    public Date getTaskCreationDate() {
        return taskCreationDate;
    }

    public void setTaskCreationDate(Date taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
    }

    public Date getTaskUpdationDate() {
        return taskUpdationDate;
    }

    public void setTaskUpdationDate(Date taskUpdationDate) {
        this.taskUpdationDate = taskUpdationDate;
    }

    public int getManpower() {
        return manpower;
    }

    public void setManpower(int manpower) {
        this.manpower = manpower;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }
}
