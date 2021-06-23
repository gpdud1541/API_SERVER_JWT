package com.api.dex.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class HttpSessionIdHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpSessionIdHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        logger.info("+HttpSessionIdHandshakeInterceptor:beforeHandshake");
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);

            logger.info(request.getHeaders().toString());

            if (session != null) {
                attributes.put("session", session);
                attributes.put("sessionId", session.getId());
            }
        }
        logger.info("+HttpSessionIdHandshakeInterceptor:beforeHandshake");
        return true;
    }

    @Override
    public void afterHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        logger.info("+HttpSessionIdHandshakeInterceptor:afterHandshake");
        logger.info("-HttpSessionIdHandshakeInterceptor:afterHandshake");
    }
}
