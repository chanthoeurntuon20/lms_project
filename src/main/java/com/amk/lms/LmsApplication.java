package com.amk.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class LmsApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(LmsApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
	}
}

