package com.rex.demo.tools.properties_resolver;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class ExcelToJsonConverter {

    protected static void excelToJson(String excelFilePath) {
        String jsonFilePath = excelFilePath.replace(".xlsx", ".json");
        FileOutputStream fileOutputStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(excelFilePath);

            // 創建工作簿對象
            Workbook workbook = new XSSFWorkbook(fileInputStream);

            // 獲取第一個工作表
            Sheet sheet = workbook.getSheetAt(0);

            // 獲取列標題
            Row headerRow = sheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();

            // 創建JSON數組，用於保存數據行
            JSONArray jsonArray = new JSONArray();

            // 迭代處理每一行（從第二行開始）
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // 跳過標題行
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // 創建JSON對象，用於保存每一行的數據
                JSONObject jsonObject = new JSONObject();

                // 迭代處理每一列
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    Cell cell = row.getCell(columnIndex);

                    // 獲取列標題作為鍵，單元格值作為值，添加到JSON對象中
                    String key = headerRow.getCell(columnIndex).getStringCellValue();
                    String value = "";
                    if (cell != null) {
                        CellType cellType = cell.getCellType();
                        if (cellType == CellType.STRING) {
                            value = cell.getStringCellValue();
                        } else if (cellType == CellType.NUMERIC) {
                            value = String.valueOf(cell.getNumericCellValue());
                        } else if (cellType == CellType.BOOLEAN) {
                            value = String.valueOf(cell.getBooleanCellValue());
                        }
                    }
                    jsonObject.put(key, value);
                }

                // 將JSON對象添加到JSON數組中
                jsonArray.put(jsonObject);
            }

            // 將JSON數組寫入文件
            fileOutputStream = new FileOutputStream(jsonFilePath);
            fileOutputStream.write(jsonArray.toString().getBytes());
            System.out.println("Excel轉換為JSON成功！");
        } catch (FileNotFoundException e) {
            System.out.println("找不到檔案: " + excelFilePath);
        } catch (IOException ioe) {
            System.out.println("發生不可預期的錯誤!");
            ioe.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ioe) {
                System.out.println("發生不可預期的錯誤!");
                ioe.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("請輸入excel檔案路徑:");
        String path = scanner.nextLine();
        System.out.println("您輸入的路徑為：" + path);
        scanner.close();
        excelToJson(path);
    }
}
