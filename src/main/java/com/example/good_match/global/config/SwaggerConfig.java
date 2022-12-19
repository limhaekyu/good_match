package com.example.good_match.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket( DocumentationType.OAS_30)
                .useDefaultResponseMessages(true) // Swagger에서 제공해주는 기본 응답코드 노출 여부
                .apiInfo(apiInfo()) // Swagger UI로 노출할 정보
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.good_match")) // api 스펙이 작성되어 있는 패키지
                .paths(PathSelectors.any()) // apis에 위치하는 API 중 특정 path 선택
                .build();
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SpringBoot Practice Rest API Documentation")
                .description("springboot rest api practice.")
                .version("0.1")
                .build();
    }
}
