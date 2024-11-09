/*package com.kw.Proj1_2020202060.controller;

import com.kw.Proj1_2020202060.model.Photo;
import com.kw.Proj1_2020202060.service.PhotoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private PhotoService photoService;

    //이미지 데이터 가져오기
    @GetMapping("/{id}/data")
    public void getImageData(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Photo photo = photoService.getPhotoById(id);
        if (photo == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // 이미지 파일을 가져오기
        File imageFile = new File(photo.getFilePath());
        if (!imageFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("image/jpeg");
        response.setContentLength((int) imageFile.length());
        response.setHeader("Content-Disposition", "inline; filename=\"" + photo.getFilename() + "\"");

        try (FileInputStream fis = new FileInputStream(imageFile);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
    //이미지 보여줌
    @GetMapping("/{id}")
    public String viewImage(@PathVariable("id") Long id) {
        return "imageview";
    }

    // 이미지 삭제
    @GetMapping("/delete/{id}")
    public String deleteImage(@PathVariable("id") Long id) {
        photoService.deletePhoto(id);
        return "redirect:/";
    }
}*/