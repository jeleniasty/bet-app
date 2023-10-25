package config;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

  @Bean
  public WebMvcConfigurer corsConfig() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry
          .addMapping("/**")
          .allowedOrigins("http://localhost:4200", "http://localhost:4200")
          .allowedMethods("*")
          .maxAge(3600L)
          .allowedHeaders("*")
          .exposedHeaders("Authorization")
          .allowCredentials(true);
      }
    };
  }
}
