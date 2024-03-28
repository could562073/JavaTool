package com.rex.demo.tools.easyexcel;

import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import org.springframework.context.MessageSource;

import java.io.OutputStream;
import java.util.Locale;

public class EasyExcelCustomFactory {

    public static ExcelWriterBuilder writeWithI18nHead(OutputStream outputStream, Class head, MessageSource messageSource, Locale locale) {
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(outputStream);
        if (head == null) {
            throw new IllegalArgumentException("head must not be null");
        }
        excelWriterBuilder.head(head);
        excelWriterBuilder.registerWriteHandler(new I18nCellWriteHandler(messageSource, locale));
        return excelWriterBuilder;
    }
}

