package com.mmg.rabbitmq.config;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * @Auther: fan
 * @Date: 2021/4/27
 * @Description:
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(true)
                .pathMapping("/")
                .apiInfo(apiInfo())
                .select()
                //匹配生成
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                .useDefaultResponseMessages(false)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("token", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("token", new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RabbitMQ的小Demo")
                .description("RabbitMQ的小Demo")
                .contact(new Contact("喵喵怪", "", "1733535060@qq.com"))
                .version("1.0")
                .build();
    }
}
