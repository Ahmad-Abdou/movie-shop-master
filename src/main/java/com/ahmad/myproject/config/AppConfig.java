package com.ahmad.myproject.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

@Configuration

public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Bean public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(3 * 1024 * 1024);
        return multipart;}

//    @Bean
//    @Order(0)
//    public MultipartFilter multipartFilter() {
//        MultipartFilter multipartFilter = new MultipartFilter();
//        multipartFilter.setMultipartResolverBeanName(" multipartResolver");
//
//        return multipartFilter; }
}
