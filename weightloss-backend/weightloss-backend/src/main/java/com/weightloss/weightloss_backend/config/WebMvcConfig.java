package com.weightloss.weightloss_backend.config;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	// Resolve to absolute path at runtime
        String absoluteUploadsPath = Paths.get(System.getProperty("user.dir"), uploadDir).toAbsolutePath().toString();

        // Ensure path format starts with "file:" so Spring serves it from filesystem
        if (!absoluteUploadsPath.startsWith("file:")) {
            absoluteUploadsPath = "file:" + absoluteUploadsPath + "/";
        }

        registry.addResourceHandler("/files/**")
                .addResourceLocations(absoluteUploadsPath)
                .setCachePeriod(3600);
    }
    
 // in a @Configuration class
    @Bean
    public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
        	registry.addMapping("/api/**")                 // restrict to your API paths
            .allowedOrigins("http://localhost:4200")
            .allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
        }
      };
    }
}
