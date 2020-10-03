package rostyk.stupnytskiy.andromeda.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Paths;

import static rostyk.stupnytskiy.andromeda.tools.FileTool.PROJECT_DIR;

@Configuration
public class StaticResourcesConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**/**/**")
                .addResourceLocations(Paths.get(PROJECT_DIR).toUri().toString());
    }
}
