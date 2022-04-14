package com.example.expenseit;

import com.example.expenseit.filter.AuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpenseItApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseItApplication.class, args);
    }


    @Bean
    public FilterRegistrationBean<AuthenticationFilter> filterFilterRegistrationBean(){
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        registrationBean.setFilter(authenticationFilter);
        registrationBean.addUrlPatterns("/categories/*");
        return registrationBean;
    }
}
