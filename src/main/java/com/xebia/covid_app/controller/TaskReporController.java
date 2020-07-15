package com.xebia.covid_app.controller;

import com.xebia.covid_app.models.TaskResponse;
import com.xebia.covid_app.service.TaskMangementService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
public class TaskReporController {
    private static final String CLASS_NAME = TaskMangementController.class.getName();

    private static final Logger LOGGER = LoggerFactory.getLogger(CLASS_NAME);

    @Autowired
    private TaskMangementService service;

    @GetMapping(value="/GenerateReport")
    public void writeExcel() {

        LOGGER.info("Inside WriteExcel method of:" + CLASS_NAME);

        List<TaskResponse> list = service.getAllRecords();
        LOGGER.info("List of Task is:" + list.toString());
        int totalTask = list.size();
        LOGGER.info("Total Task is:" + totalTask);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet("Task_Details");

        for(int i=0;i<totalTask;i++) {

            if(i==0){
                HSSFRow rowhead = sheet.createRow((short) 0);
                rowhead.createCell(0).setCellValue("Sr. No.");
                rowhead.createCell(1).setCellValue("ID");
                rowhead.createCell(2).setCellValue("AssignToId");
                rowhead.createCell(3).setCellValue("Comments");
                rowhead.createCell(4).setCellValue("FrequencyId");
                rowhead.createCell(5).setCellValue("LocationId");
                rowhead.createCell(6).setCellValue("Status");
            }

            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(i + 1); // Sr. No.
            row.createCell(1).setCellValue(list.get(i).getId());

            row.createCell(2).setCellValue(list.get(i).getAssignToId());
            row.createCell(3).setCellValue(list.get(i).getComments());
            row.createCell(4).setCellValue(list.get(i).getFrequencyId());
//            row.createCell(5).setCellValue(list.get(i).getLocationId());

            row.createCell(6).setCellValue(list.get(i).getStatus());

        }


    }
}
