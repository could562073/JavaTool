package com.rex.demo.enums;

import lombok.Getter;

@Getter
public enum ExcelFileNameEnum {
    EXPORT_NAME("EXPORT_NAME", "導出數據");

    ExcelFileNameEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    private final String code;

    private final String trans;
}
