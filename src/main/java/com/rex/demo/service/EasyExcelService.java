package com.rex.demo.service;

import com.rex.demo.iservice.IEasyExcelService;
import com.rex.demo.tools.easyexcel.EasyExcelCustomFactory;
import com.rex.demo.tools.easyexcel.EasyExcelStyleStrategy;
import com.rex.demo.enums.ExcelFileNameEnum;
import com.rex.demo.vo.EasyExcelVo;
import com.rex.demo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class EasyExcelService implements IEasyExcelService {

    @Autowired
    private MessageSource messageSource;

    public ResponseEntity<byte[]> export() {

        UserVo userVo = new UserVo();
        userVo.setUsername("test");
        userVo.setStatus("test");
        userVo.setCreateTime(new Date());

        List<UserVo> dataList = new ArrayList<>();
        dataList.add(userVo);
        dataList.add(userVo);
        dataList.add(userVo);
        dataList.add(userVo);
        dataList.add(userVo);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        EasyExcelCustomFactory.writeWithI18nHead(outputStream, EasyExcelVo.class, messageSource, Locale.of("zh_TW"))
                .registerWriteHandler(EasyExcelStyleStrategy.setHorizontalCellStyleStrategy())
                .sheet("player")
                .doWrite(dataList);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, setFileConfig())
                .body(outputStream.toByteArray());
    }

    /**
     * 設置檔案屬性: attachment; filename; encoding;
     *
     * @return header
     */
    private String setFileConfig() {
        String fileName = ExcelFileNameEnum.EXPORT_NAME.getTrans() + "-" + System.currentTimeMillis() + ".xlsx";
        //防止語系亂碼
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        return "attachment; filename=" + encodedFileName + ";filename*=utf-8''" + encodedFileName;
    }
}
