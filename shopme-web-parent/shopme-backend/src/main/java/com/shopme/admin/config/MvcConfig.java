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
        String userDirName = "user-photos";
        Path userPhotoDir = Paths.get(userDirName);
        String userPhotosPath = userPhotoDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/" + userDirName + "/**") // map the absolute path
                .addResourceLocations("file:///" + userPhotosPath + "/");

        String catDirName = "../category-images";
        Path catImageName = Paths.get(catDirName);
        String catImagesPath = catImageName.toFile().getAbsolutePath();
        registry.addResourceHandler("/category-images/**") // map the absolute path
                .addResourceLocations("file:///" + catImagesPath + "/");

        String brandDirName = "../brand-logos";
        Path brandLogoName = Paths.get(brandDirName);
        String brandLogoPath = brandLogoName.toFile().getAbsolutePath();
        registry.addResourceHandler("/brand-logos/**") // map the absolute path
                .addResourceLocations("file:///" + brandLogoPath + "/");


    }
}
