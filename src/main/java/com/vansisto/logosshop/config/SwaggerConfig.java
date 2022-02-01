package com.vansisto.logosshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket swaggerConfiguration(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/**"))
                .apis(RequestHandlerSelectors.basePackage("com.vansisto.logosshop"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "Logos shop API",
                "API for final logos project",
                "0.1",
//				"some.terms.of.service.url",
                "http://some.terms.of.service.url",
                new springfox.documentation.service.Contact("Van Sisto", "", "vansisto@gmail.com"),
                "API license",
                "http://some.license.url",
                Collections.emptyList()
        );
    }
}
