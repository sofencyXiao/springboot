package com.sofency.session_listener.config;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sofency
 * @date 2020/5/12 10:41
 * @package IntelliJ IDEA
 * @description
 */
public class SessionListenerCount implements HttpSessionListener {
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        atomicInteger.getAndIncrement();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        atomicInteger.getAndDecrement();
    }
}
