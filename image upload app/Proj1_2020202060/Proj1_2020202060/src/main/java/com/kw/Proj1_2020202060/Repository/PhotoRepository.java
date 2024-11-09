package com.kw.Proj1_2020202060.Repository;

import com.kw.Proj1_2020202060.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;



 //PhotoRepository 인터페이스는 JpaRepository를 상속하여 
 //데이터베이스와의 상호작용을 처리
 //JpaRepository는 기본적인 CRUD (Create, Read, Update, Delete) 
 //메서드를 제공하며, Photo 엔티티와 Long 타입의 ID를 사용함
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}