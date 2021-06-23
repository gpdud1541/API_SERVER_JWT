package com.api.dex.config;

import com.api.dex.listener.ServletContextListenerImpl;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class CommonConfig {

    // Multipart 파일 Resolver
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSizePerFile(300 * 1024 * 1024); // 100MB
        return  commonsMultipartResolver;
    }

    @Bean
    public ServletListenerRegistrationBean<ServletContextListenerImpl> servletContextListener() {
        // 톰캣에 의해서 실행될때 호출
        ServletListenerRegistrationBean<ServletContextListenerImpl> listenerRegBean = new ServletListenerRegistrationBean<>();
        listenerRegBean.setListener(new ServletContextListenerImpl());
        return listenerRegBean;
    }

}
