package com.kw.Proj1_2020202060.service;

import com.kw.Proj1_2020202060.Repository.*;
import com.kw.Proj1_2020202060.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Service
public class PhotoService {
    // PhotoRepository를 통해 데이터베이스와 상호작용
    @Autowired
    private PhotoRepository photoRepository;

    //모든 사진 가져오기 최신순으로 정렬
    public List<Photo> getAllPhotos() {
        var list = photoRepository.findAll();
        Collections.reverse(list);
        return list;
    }
    //특정 id의 사진 정보를 가져오는 매서드
    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    //사진을 저장하는 함수
    public void savePhoto(String title, String comment, MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        file.transferTo(new File(System.getProperty("user.home")+"\\"+/*+   "\\Proj1_DB_2020202060\\"*/ filename));

        Photo photo = new Photo();
        photo.setTitle(title); //제목
        photo.setComment(comment); //커멘트
        photo.setFilename(filename); //파일

        //************* 조교님 홈 디렉토리에 사진을 저장하게 되어서 죄송합니다**************
        photo.setFilePath(System.getProperty("user.home")/*+   "\\Proj1_DB_2020202060\\"*/);
        photoRepository.save(photo);
    }
    //사진을 삭제하는 함수
    public void deletePhoto(Long id) {
        System.out.println(id);
        photoRepository.deleteById(id);
    }
    //id를 가져와 사진을 수정하는 함수
    public void updatePhoto(Long id, String title, String comment, MultipartFile file) throws IOException {
        // 주어진 ID로 기존의 Photo 객체를 조회
        Photo photo = photoRepository.findById(id).orElse(null);
        if (photo != null) {
            // 새로운 제목과 코멘트를 설정합니다.
            photo.setTitle(title);
            photo.setComment(comment);
            // 만약 파일이 비어있지 않다면 파일명과 파일 경로를 업데이트
            if (file != null && !file.isEmpty()) {
                String filename = file.getOriginalFilename();
                // 파일을 지정된 디렉토리로 전송
                file.transferTo(new File(System.getProperty("user.home")+   "\\" +/*Proj1_DB_2020202060\\"*/  filename));

                // Photo 객체에 새로운 파일명과 파일 경로를 설정
                photo.setFilename(filename);
                photo.setFilePath(System.getProperty("user.home")/*+   "\\Proj1_DB_2020202060\\"*/);
            }
            // 변경된 내용을 데이터베이스에 저장합니다.
            photoRepository.save(photo);
        }
    }
}