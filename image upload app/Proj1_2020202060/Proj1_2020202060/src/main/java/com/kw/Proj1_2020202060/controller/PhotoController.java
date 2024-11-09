package com.kw.Proj1_2020202060.controller;
import com.kw.Proj1_2020202060.model.*;
import com.kw.Proj1_2020202060.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



 //PhotoController 클래스는 사용자의 요청을 처리하고,
 //그에 따라 적절한 서비스 메서드를 호출하여 모델과 뷰 사이의 상호작용을 관리

@Controller
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // get 요청에 대한 핸들러 메서드 홈페이지를 보여준다
    @GetMapping("/")
    public String viewHomePage(Model model) {
        // 모든 사진을 모델에 추가
        model.addAttribute("photos", photoService.getAllPhotos());

        return "index";
    }
    @GetMapping("/index.html")
    public String viewHomePage1(Model model) {
        // 모든 사진을 모델에 추가
        model.addAttribute("photos", photoService.getAllPhotos());

        return "index";
    }

    // GET 요청에 대한 핸들러 메서드. 파일 업로드 폼을 보여줌
    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload"; // "upload" 템플릿을 반환
    }
    //파일 업로드
    @PostMapping("/upload")
    public String uploadPhoto(@RequestParam("title") String title,
                              @RequestParam("comment") String comment,
                              @RequestParam("file") MultipartFile file) throws IOException {
        photoService.savePhoto(title, comment, file);   // 사진을 저장
        return "redirect:/";
    }

    //특정 이미지를 보여줌
    @GetMapping("/imageview/{id}")
    public String viewPhotoDetails(@PathVariable("id") Long id, Model model) {
        // ID로 사진을 조회
        Photo photo = photoService.getPhotoById(id);
        // 조회한 사진을 모델에 추가
        model.addAttribute("photo", photo);
        // 콘솔에 사진 정보를 출력
        System.out.println(photo);
        // "imageview" 템플릿을 반환
        return "imageview";
    }
    //이미지 삭제
    @GetMapping("/delete/{id}")
    public String deletePhoto(@PathVariable("id") Long id) {
        photoService.deletePhoto(id); // ID로 사진을 삭제
        return "redirect:/";
    }
    //이미지 수정 폼을 보여줌
    @GetMapping("/upload/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        // ID로 사진을 조회
        Photo photo = photoService.getPhotoById(id);
        // 조회한 사진을 모델에 추가
        model.addAttribute("photo", photo);
        return "upload";
    }
    //이미지 수정
    @PostMapping("/update/{id}")
    public String updatePhoto(@PathVariable("id") Long id,
                              @RequestParam("title") String title,
                              @RequestParam("comment") String comment,
                              @RequestParam("file") MultipartFile file) throws IOException {
        // ID로 사진을 수정
        photoService.updatePhoto(id, title, comment, file);
        // 홈페이지로 리디렉션
        return "redirect:/";
    }
}