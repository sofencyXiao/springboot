package com.sofency.session_listener.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sofency
 * @date 2020/5/12 10:53
 * @package IntelliJ IDEA
 * @description
 */
@Configuration
public class SessionConfiguration  implements WebMvcConfigurer  {

    @Bean
    public ServletListenerRegistrationBean getListener() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new SessionListenerCount());
        return servletListenerRegistrationBean;
    }
}
