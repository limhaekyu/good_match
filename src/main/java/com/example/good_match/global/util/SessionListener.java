package com.example.good_match.global.util;


import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    @Value("${server.servlet.session.timeout}")
    private int sessionTime;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(sessionTime);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }

}
