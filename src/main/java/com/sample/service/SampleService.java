package com.sample.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sample.dao.SampleDAO;

@Service
public class SampleService {
    // This is a sample service for demonstration purposes
    private final SampleDAO sampleDAO;

    public SampleService(SampleDAO sampleDAO) {
        this.sampleDAO = sampleDAO;
    }

    // Example method
    public String sampleMethod() {
        // Perform some logic here
        String data = sampleDAO.getSampleData();
        if(StringUtils.hasText(data)){
            return "Processed: " + data;
        }
        else {
            return "No data available";
        }
    }
}
