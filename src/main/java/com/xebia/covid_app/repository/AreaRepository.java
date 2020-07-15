package com.xebia.covid_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xebia.covid_app.entities.Area;

public interface AreaRepository extends JpaRepository<Area, Integer> {

    Area findByArea(String area);
}
