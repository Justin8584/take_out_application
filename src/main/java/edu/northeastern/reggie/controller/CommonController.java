package edu.northeastern.reggie.controller;

import edu.northeastern.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * file upload and download
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    // user home directory
    private final String userHome = System.getProperty("user.home");

    @Value("${reggie.path}")
    private String basePath;

    /**
     * file upload
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        // file is a temporary file, need to save to somewhere else
//        log.info(file.toString());

        // original file name
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // use UUID rename the file name, prevent the same name that might over-ride the old file
        String filename = UUID.randomUUID().toString() + suffix;

        // create a new directory
        File dir = new File(userHome + basePath);
        // check the directory exist
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            // save the temporary to the certain directory
            file.transferTo(new File(userHome + basePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            // input stream, read the input file
            FileInputStream fileInputStream = new FileInputStream(new File(userHome + basePath + name));
            // output stream, put the file back to browser, show img.
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();  // 刷新
            }
            outputStream.close(); // close recourse
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
