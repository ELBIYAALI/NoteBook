package org.sid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("services")
@SpringBootApplication
@ComponentScan
public class NoteBookServerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NoteBookServerApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(NoteBookServerApplication.class, args);
	}
}
