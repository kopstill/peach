package com.kopever.peach.service.meituan.configuration;

import com.kopever.peach.service.meituan.filter.TestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lullaby on 2018/5/24
 */
@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        TestFilter filter = new TestFilter();
        registrationBean.setFilter(filter);

        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);

        return registrationBean;
    }

}
