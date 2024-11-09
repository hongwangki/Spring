package com.kw.Proj1_2020202060.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 리소스 핸들러를 추가. /imgs/** 경로로 시작하는 모든 요청은
        // 사용자의 홈 디렉토리에 있는 파일로 매핑
        registry.addResourceHandler("/imgs/**")
                // 파일의 실제 경로를 지정
                // System.getProperty("user.home")는 사용자 홈 디렉토리를 나타내어
                // 그 뒤에 "\\"를 추가하여 파일 경로를 설정
                .addResourceLocations("file:"+ System.getProperty("user.home")+"\\");


    }
}