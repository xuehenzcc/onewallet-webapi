package com.group.wallet;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.group"})
@MapperScan(basePackages = {"com.group.core.mapper","com.group.wallet.mapper"})
@EnableTransactionManagement
@EnableAsync
@EnableCaching
public class Application extends SpringBootServletInitializer{

    //tomcat war包启动
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


    //jar启动
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}