package com.imnu.cloudDisk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
//@ComponentScan(basePackages ={"com.google.code" })
@MapperScan("com.imnu.cloudDisk.mapper")
public class CloudDiskApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CloudDiskApplication.class, args);
    }

//    @Override
//            protected SpringApplicationBuilder configure(SpringApplicationBuilder bulder) {
//        return super.configure(builder);
//    }
}
