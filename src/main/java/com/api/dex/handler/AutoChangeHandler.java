package com.api.dex.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoChangeHandler {
    private static final Logger logger = LoggerFactory.getLogger(AutoChangeHandler.class);

    public static int lastContentCount = 0;

    @Scheduled(cron = "0 0 0 * * 0") // 매일 00시 (초, 분, 시, 일, 월, 요일) / 매주 일요일 0시
//    @Scheduled(cron = "0 5 * * * *") // 5분 마다 // == @Scheduled(fixedDelay = 1000)
    public void changeMainHallContent() {

    }
}
