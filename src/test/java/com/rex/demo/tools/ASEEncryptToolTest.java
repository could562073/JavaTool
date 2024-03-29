package com.rex.demo.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ASEEncryptToolTest {
    @Test
    public void test() {
        try {
            String inputText = "V-kr5GKrf9AisXIAEUNLEG0N";
            String password = "F202XKa5sE4HoDmu";

            // 加密
            String encryptedText = AESEncryptTool.encrypt(inputText, password);
            System.out.println("加密後的結果：" + encryptedText);

            // 解密
            String decryptedText = AESEncryptTool.decrypt(encryptedText, password);
            System.out.println("解密後的結果：" + decryptedText);

            assertEquals(inputText, decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
