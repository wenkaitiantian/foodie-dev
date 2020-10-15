package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
//配置Swagger2核心配置docket
    //http://localhost:8088/swagger-ui.html
    @Bean
    public Docket  createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2) //指定api类型为Swagger2
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.imooc.controller")) //指定colltroller包
                .paths(PathSelectors.any())
                .build();                             // 用于定义Api文档信息
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("天天吃货平台")      //文档标题
                .contact(new Contact("imooc",
                        "https://www.imooc.com",
                        "abc@imooc.com"))  //联系人
                .description("天天api文档") //详细信息
                .version("1.1.1")        //文档版本好
                .termsOfServiceUrl("https://www.imooc.com")
                .build();
    }
}
