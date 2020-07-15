package com.xebia.covid_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xebia.covid_app.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
