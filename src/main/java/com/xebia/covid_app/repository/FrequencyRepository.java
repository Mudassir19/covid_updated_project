package com.xebia.covid_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xebia.covid_app.entities.Frequency;


public interface FrequencyRepository extends JpaRepository<Frequency, String> {

}
