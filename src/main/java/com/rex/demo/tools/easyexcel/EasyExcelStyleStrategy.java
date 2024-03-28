package com.rex.demo.tools.easyexcel;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class EasyExcelStyleStrategy {
    /**
     * @Description: 自定義表格的標題和內容的樣式
     * 用法示範：
     * 註冊樣式並寫入excel
     * EasyExcel
     * .write(String pathName, Class head)
     * .sheet(String sheetName)
     * .doWrite(List data);
     * @Date: 2021/02/19 11:38
     **/
    public static HorizontalCellStyleStrategy setHorizontalCellStyleStrategy() {
        // 標頭策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 標頭文字
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 8);
        headWriteFont.setFontName("DejaVu Sans Mono");
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 儲存格策略
        WriteCellStyle contentWriteCellStyle = getWriteCellStyle();

        // 這個策略是 標頭是標頭的樣式 內容是內容的樣式 其他的策略可以自己實現
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    private static WriteCellStyle getWriteCellStyle() {
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        // 儲存格字體大小
        contentWriteFont.setFontHeightInPoints((short) 8);
        contentWriteFont.setFontName("DejaVu Sans Mono");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 設置 自動換行
        contentWriteCellStyle.setWrapped(true);
        // 設置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 設置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return contentWriteCellStyle;
    }
}
