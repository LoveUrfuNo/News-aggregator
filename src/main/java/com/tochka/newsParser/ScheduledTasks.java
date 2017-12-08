package com.tochka.newsParser;

import com.sun.syndication.io.FeedException;

import com.tochka.newsParser.services.NewsContentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private NewsContentService newsContentService;

    @Scheduled(fixedRate = 60000)
    public void checkNews() {
        try {
            newsContentService.saveNews();
        } catch (IOException | FeedException e) {
            logger.warn("News save exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
