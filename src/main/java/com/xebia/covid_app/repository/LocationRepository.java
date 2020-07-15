package com.xebia.covid_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.xebia.covid_app.entities.Location;
import org.springframework.data.repository.Repository;

public interface LocationRepository extends JpaRepository<Location, String> {

    Location findByLocation(String location);
}
