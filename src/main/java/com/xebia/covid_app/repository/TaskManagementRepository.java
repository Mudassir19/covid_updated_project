package com.xebia.covid_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xebia.covid_app.entities.Task;

@Repository
public interface TaskManagementRepository extends JpaRepository<Task, Integer> {

}
