package com.rex.demo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.enums.BooleanEnum;

import java.io.Serializable;
import java.util.Date;


@lombok.Getter
@lombok.Setter
@ContentRowHeight(14)
@HeadRowHeight(20)
@ColumnWidth
public class EasyExcelVo implements Serializable {


    @ColumnWidth(19)
    @ExcelProperty(value = "${excel.player.username}")
    @HeadFontStyle(bold = BooleanEnum.TRUE)
    private String username;

    @ColumnWidth(19)
    @ExcelProperty(value = "${excel.player.status}")
    @HeadFontStyle(bold = BooleanEnum.TRUE)
    private String status;

    @ColumnWidth(30)
    @ExcelProperty(value = "${excel.player.createTime}")
    @HeadFontStyle(bold = BooleanEnum.TRUE)
    private Date createTime;
}
