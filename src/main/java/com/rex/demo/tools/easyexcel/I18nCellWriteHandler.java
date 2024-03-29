package com.rex.demo.tools.easyexcel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

public class I18nCellWriteHandler implements CellWriteHandler {

    private final MessageSource messageSource;

    private final Locale locale;

    public I18nCellWriteHandler(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        if (Boolean.TRUE.equals(isHead)) {
            List<String> originHeadNames = head.getHeadNameList();
            if (CollectionUtils.isNotEmpty(originHeadNames)) {
                List<String> newHeadNames = originHeadNames.stream()
                        .map(headName -> PlaceholderResolver.getDefaultResolver()
                                .resolveByRule(headName, (name) -> messageSource.getMessage(name, null, locale)))
                        .toList();
                head.setHeadNameList(newHeadNames);
            }
        }
    }
}

