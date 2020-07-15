package com.xebia.covid_app.service.impl;

import com.xebia.covid_app.entities.Task;
import com.xebia.covid_app.models.PieChart;
import com.xebia.covid_app.repository.TaskManagementRepository;
import com.xebia.covid_app.service.PieChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PieChartServiceImpl implements PieChartService {

    @Autowired
    TaskManagementRepository taskRepository;

    @Override
    public List<PieChart> createStatusList() {
        List<PieChart> statusList = new ArrayList<>();
        List<Task> taskList = taskRepository.findAll();

        int completedCount=0; //S001
        int upcomingCount=0;  //S002
        int pendingCount=0;   //S003

        for (int i = 0; i <taskList.size(); i++) {
            if(taskList.get(i).getStatus().getId().equals("S101")){
                completedCount++;
            }
            else if(taskList.get(i).getStatus().getId().equals("S102")){
                upcomingCount++;
            }
            else if(taskList.get(i).getStatus().getId().equals("S103") || taskList.get(i).getStatus().getId().equals("S104")){
                pendingCount++;
            }
        }

        PieChart completed = new PieChart("Completed",completedCount);
        PieChart upcoming = new PieChart("Upcoming",upcomingCount);
        PieChart pending = new PieChart("Pending",pendingCount);

        statusList.add(completed);
        statusList.add(upcoming);
        statusList.add(pending);
        return statusList;
    }
}
