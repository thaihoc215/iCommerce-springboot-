package com.shopme.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // to expose directory on the file system to accessible by client
        String dirName = "user-photos";
        Path userPhotoDir = Paths.get(dirName);
        String userPhotosPath = userPhotoDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + dirName + "/**") // map the absolute path
                .addResourceLocations("file:///" + userPhotosPath + "/");
    }
}
