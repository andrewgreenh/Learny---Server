package de.learny.swagger;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import static com.google.common.collect.Lists.*;

@SpringBootApplication
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket learnyApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	    	.apiInfo(apiInfo())
	        .select()
	          .apis(RequestHandlerSelectors.any())
	          .paths(PathSelectors.any())
	          .build()
	        .pathMapping("/")
	        .directModelSubstitute(LocalDate.class,
	            String.class)
	        .genericModelSubstitutes(ResponseEntity.class)
	        .useDefaultResponseMessages(false)
	        .securitySchemes(newArrayList(apiKey()))
	        .securityContexts(newArrayList(securityContext()))
	        ;
	  }
	
	private ApiKey apiKey() {
	    return new ApiKey("mykey", "api_key", "header");
	  }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Learny Api")
                .description("Hier steht die Beschreibung der Api")
                .termsOfServiceUrl("http://springfox.io")
                .contact("springfox")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
	private SecurityContext securityContext() {
	    return SecurityContext.builder()
	        .securityReferences(defaultAuth())
	        .forPaths(PathSelectors.regex("/anyPath.*"))
	        .build();
	  }
	
	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	        = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return newArrayList(
	        new SecurityReference("mykey", authorizationScopes));
	  }
	
	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(
	        "test-app-client-id",
	        "test-app-realm",
	        "test-app",
	        "apiKey");
	  }

}

