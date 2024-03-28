package com.rex.demo.tools.properties_resolver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class PropertiesSortTool {
    public static void main(String[] args) {
        sortProperties();
    }

    /**
     * 將properties內文字由A-Z調換
     */
    public static void sortProperties () {
       String fileName = "C:\\java_workspace\\sideProject\\javaTool\\target\\classes\\common_pt_BR.properties";
       String sortedFileName = "C:\\java_workspace\\sideProject\\javaTool\\target\\classes\\sort_common_pt_BR.properties";

       ArrayList<String> lines = new ArrayList<>();

       try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sortedFileName), StandardCharsets.UTF_8))) {
           String line;
           while ((line = br.readLine()) != null) {
               lines.add(line);
           }

           lines.sort((line1, line2) -> {
               String key1 = "";
               String key2 = "";
               if (line1.contains("=")) {
                   key1 = line1.substring(0, line1.indexOf("="));
               }
               if (line2.contains("=")) {
                   key2 = line2.substring(0, line2.indexOf("="));
               }
               return key1.compareTo(key2);
           });

           for (String readLine : lines) {
               bw.write(readLine);
               bw.newLine();
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
}
