package com.rex.demo.tools.properties_resolver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/***
 * unicdoe轉換(可針對properties內文在做花式處理)
 * 使用方法:
 * 1. 將此檔案放置該專案底下
 * ex: app_mcenter\mcenter\java\so\wwb\gamebox\mcenter\
 * 2. ROOT_PATH 改為該專案目錄
 * ex: app_mcenter\mcenter
 */
public class UnicodeResolver {
    /**
     *
     */
    private static String ROOT_PATH = "src/main";

    /**
     * 處理過的檔案列表
     */
    private static List<String> FILE_NAMES = new ArrayList<>();

    /**
     * 當前檔案名稱;
     */
    private static String FILE_NAME = "";

    private static String FILE_PATH = "";

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        testForSpeed(10);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("耗費時間: " + executionTime + "毫秒");

    }

    public static void testForSpeed(int excuteTimes) {
        for (int i = 0; i < excuteTimes; i++) {
            String result1 = convertUnicodeToUTF8("dicts");
            System.out.println(result1);
            String result2 = convertUnicodeToUTF8("messages");
            System.out.println(result2);
            String result3 = convertUnicodeToUTF8("views");
            System.out.println(result3);
        }
    }

    /**
     * 讀取檔案名稱
     *
     * @return
     */
    protected static String convertUnicodeToUTF8(String subPath) {
        String resourcesPath = ROOT_PATH + "/resources/conf/i18n/" + subPath;
        readFileNames(resourcesPath);
        return "讀取了" + FILE_NAMES.size() + "檔案";
    }

    /**
     * 讀取檔案名稱
     *
     * @param directoryPath
     */
    private static void readFileNames(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                System.out.println("Files in directory: " + directoryPath);
                for (File file : files) {
                    if (file.isFile()) {
                        FILE_NAME = file.getName();
                        FILE_NAMES.add(file.getName());
                        FILE_PATH = file.getAbsolutePath();
                        fileHandleProcess(file.getAbsolutePath());
                    }
                }
            } else {
                System.out.println("No files found in the directory.");
            }
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }
    }

    /**
     * 檔案處理
     */
    public static void fileHandleProcess(String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        InputStream inputStream = UnicodeResolver.class.getClassLoader().getResourceAsStream(getInputPath(filePath));
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(filePath)), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                lines.add(convert(line));
            }
            for (int i = 0; i < lines.size(); i++) {
                String lineString = lines.get(i).trim();
                //修正井字號全型
                if (lineString.contains("＃")) {
                    lineString = lineString.replace("＃", "#");
                }
                bw.write(lineString);
                //最後一行不做換行
                if (i != lines.size() - 1) {
                    bw.newLine();
                }
            }
            System.out.println("轉換完畢! " + FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            System.out.println("此檔案路徑不存在檔案:" + FILE_PATH);
        }
    }

    private static String getInputPath(String filePath) {
        int startIndex = filePath.indexOf("\\conf") + 1;
        int endIndex = filePath.length();
        return filePath.substring(startIndex, endIndex);
    }


    public static String convert(String input) {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        while (index < input.length()) {
            if (input.charAt(index) == '\\' && index + 1 < input.length() && input.charAt(index + 1) == 'u') {
                String unicodeSequence = input.substring(index + 2, index + 6);
                char unicodeChar = (char) Integer.parseInt(unicodeSequence, 16);
                sb.append(unicodeChar);
                index += 6;
            } else {
                sb.append(input.charAt(index));
                index++;
            }
        }

        return sb.toString();
    }

}
