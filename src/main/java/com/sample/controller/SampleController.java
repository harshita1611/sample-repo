package com.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.service.SampleService;

@RestController
public class SampleController {
    private final SampleService sampleService;

    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }
    // This is a sample controller for demonstration purposes

//     Example endpoint
     @GetMapping("/sample")
     public String sampleEndpoint() {
         // call service or perform some logic here
            String result = sampleService.sampleMethod();
            if (result != null && !result.isEmpty()) {
                return result;
            } else {
                return "No data available from SampleService";
            }
     }
}
