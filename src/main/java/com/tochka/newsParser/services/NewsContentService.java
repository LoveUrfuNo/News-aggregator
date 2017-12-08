package com.tochka.newsParser.services;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import com.tochka.newsParser.models.NewsContent;
import com.tochka.newsParser.models.NewsSite;
import com.tochka.newsParser.repositories.NewsContentRepository;
import com.tochka.newsParser.repositories.NewsSiteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class NewsContentService {

    @Autowired
    private NewsContentRepository newsContentRepository;

    @Autowired
    private NewsSiteRepository newsSiteRepository;

    public void saveNews() throws IOException, FeedException {

        for (NewsSite site : newsSiteRepository.findAll()) {
            final SyndFeed feed = new SyndFeedInput()
                    .build(new XmlReader(new URL(site.getUrl())));
            final List<NewsContent> oldNewsList = newsContentRepository
                    .findAllBySourceNewsSiteId(site.getId());

            final List<SyndEntryImpl> latestNewsEntries = feed.getEntries();
            latestNewsEntries.forEach(latestNews -> {
                if (oldNewsList.stream().noneMatch(oldNews ->
                        oldNews.getUri().equals(latestNews.getUri()))) {
                    newsContentRepository.save(getNewsContent(latestNews, site));
                }
            });
        }
    }

    private NewsContent getNewsContent(SyndEntryImpl newsEntry, NewsSite site) {
        NewsContent newsContent = new NewsContent();

        newsContent.setSourceNewsSiteId(site.getId());
        newsContent.setUri(newsEntry.getUri());
        newsContent.setTitle(newsEntry.getTitle());
        newsContent.setDescription(newsEntry.getDescription().getValue());
        newsContent.setPublishDate(newsEntry.getPublishedDate());

        if (site.isAuthor()) {
            newsContent.setAuthor(newsEntry.getAuthor());
        }
        if (site.isLink()) {
            newsContent.setLink(newsEntry.getLink());
        }

        return newsContent;
    }
}
