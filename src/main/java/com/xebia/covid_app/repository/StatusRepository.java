package com.xebia.covid_app.repository;

import com.xebia.covid_app.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status,String> {
}
