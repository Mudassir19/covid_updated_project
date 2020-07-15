package com.xebia.covid_app.service;

import com.xebia.covid_app.models.PieChart;

import java.util.List;

public interface PieChartService {
    List<PieChart> createStatusList();
}
