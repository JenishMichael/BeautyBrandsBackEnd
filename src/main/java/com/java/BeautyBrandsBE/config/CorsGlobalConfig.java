package com.java.BeautyBrandsBE.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// --------------------------------------------
// ✅ @Configuration:
// Marks this class as a configuration class.
// When Spring Boot starts, it will look for classes annotated with @Configuration
// and treat them as sources of bean definitions.
// --------------------------------------------
@Configuration
public class CorsGlobalConfig {

    // --------------------------------------------
    // ✅ @Bean:
    // Spring will call this method and store the return value (WebMvcConfigurer object)
    // in its ApplicationContext (Spring container).
    // This object can then be used globally throughout the application.
    // --------------------------------------------
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        // --------------------------------------------
        // ✅ WebMvcConfigurer is an interface that provides multiple default methods.
        // Instead of creating a separate class that implements this interface,
        // we are using an **anonymous inner class** to override only the needed method: addCorsMappings().
        //
        // ❓ What is an Anonymous Class?
        // It is a class without a name, defined and instantiated at the same time.
        // It's useful when you only need to override one or two methods temporarily.
        //
        // 👉 Here, we override just one method of WebMvcConfigurer:
        //     addCorsMappings(CorsRegistry registry)
        // to customize global CORS behavior.
        // --------------------------------------------
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                // --------------------------------------------
                // ✅ registry.addMapping("/**")
                // Apply this CORS policy to **all** endpoints (/** means any URL)
                // --------------------------------------------

                registry.addMapping("/**")

                        // ✅ Allow requests from specific frontend origins (React frontend)
                        // "*" is not allowed when allowCredentials(true) is used.
                        .allowedOrigins(
                                "http://localhost:5173",              // Development (React)
                                "https://beautybrandslisting.netlify.app" // Production (Netlify hosted)
                        )

                        // ✅ Allow these HTTP methods from frontend to backend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")

                        // ✅ Allow all request headers (like Authorization, Content-Type)
                        .allowedHeaders("*")

                        // ✅ Allow credentials (like cookies, Authorization headers)
                        // Only works if allowedOrigins is NOT "*"
                        .allowCredentials(true);
            }
        };
    }
}
