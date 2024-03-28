package com.rex.demo.controller;

import com.rex.demo.iservice.IEasyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private IEasyExcelService easyExcelService;

    @PostMapping("/export")
    public ResponseEntity<byte[]> test() {
        return easyExcelService.export();
    }
}
