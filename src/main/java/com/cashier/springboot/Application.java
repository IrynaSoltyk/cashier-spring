package com.cashier.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public ViewResolver thymeleafViewResolver() {

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setOrder(0);

        return viewResolver;
    }

    // Thymeleaf template engine with Spring integration
    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.setEnableSpringELCompiler(true);

        return templateEngine;
    }

    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver() {
        return new SpringResourceTemplateResolver();
    }

    // Thymeleaf template resolver serving HTML 5
    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }
}
