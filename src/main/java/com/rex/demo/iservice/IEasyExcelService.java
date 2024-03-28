package com.rex.demo.iservice;

import org.springframework.http.ResponseEntity;

public interface IEasyExcelService {
    ResponseEntity<byte[]> export();
}
