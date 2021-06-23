package com.api.dex.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    // httpsession이 생성 및 삭제되었을 때
    private static final Logger logger = LoggerFactory.getLogger(HttpSessionListenerImpl.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession httpSession = httpSessionEvent.getSession();
        logger.info("*****************************************************************************************");
        logger.info("HttpSessionListenerImpl:sessionCreated:session ID= " + httpSession.getId());
        logger.info("*****************************************************************************************");

        if(httpSessionEvent.getSession().isNew()){
        }

        // 초 단위로 계산. 30분 Interval 적용
//        httpSession.setMaxInactiveInterval(SESSIONMAXINACTIVEINTERVAL_MIN*60);
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession httpSession = httpSessionEvent.getSession();
        String sessionId = httpSession.getId();

        logger.info("********************************************************************************************");
        logger.info("===============HttpSessionListenerImpl:sessionDestroyed:httpSession:" + httpSession );
        logger.info("===============HttpSessionListenerImpl:sessionDestroyed:session ID= " + sessionId);
        logger.info("********************************************************************************************");
    }

}
