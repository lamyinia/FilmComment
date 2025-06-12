package com.example.controller;

import cn.hutool.core.io.FileUtil;
import com.example.common.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件相关操作接口
 */
@RestController

@RequestMapping("/files")
public class FileController {
    private static final String filePath = System.getProperty("user.dir") + "/files/";

    @Value("${fileBaseUrl}")
    private String fileBaseUrl;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        // 定义文件的唯一标识
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        // 拼接完整的文件存储路径
        String realFilePath = filePath + fileName;
        try {
            if (!FileUtil.isDirectory(filePath)) {
                FileUtil.mkdir(filePath);
            }
            FileUtil.writeBytes(file.getBytes(), realFilePath);
        } catch (IOException e) {
            System.out.println("文件上传错误");
        }
        String url = fileBaseUrl + "/files/download/" + fileName;
        return Result.success(url);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));

        String realFilePath = filePath + fileName;
        try {
            byte[] bytes = FileUtil.readBytes(realFilePath);
            ServletOutputStream os = response.getOutputStream();

            os.write(bytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            System.out.println("文件下载错误");
        }
    }

}
