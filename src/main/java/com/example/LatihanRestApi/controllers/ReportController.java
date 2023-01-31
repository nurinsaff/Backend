package com.example.LatihanRestApi.controllers;

import com.example.LatihanRestApi.models.entity.User;
import com.example.LatihanRestApi.services.GetReportService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    GetReportService getReportService;

    @GetMapping("/getreport/{localDate}")
    public ResponseEntity<Object> getReport(@PathVariable LocalDate localDate){

        return new ResponseEntity<>(getReportService.getReport(localDate), HttpStatus.OK);
//        return ResponseEntity.ok().body(getReportService.getReport(localDate));
    }
}
