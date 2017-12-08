package com.tochka.newsParser.services;

import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import com.tochka.newsParser.models.NewsSite;
import com.tochka.newsParser.repositories.NewsSiteRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class NewsSiteService {

    @Autowired
    private NewsSiteRepository newsSiteRepository;

    public boolean isSiteValid(NewsSite newsSite) {
        final String url = newsSite.getUrl();

        if (!url.contains("http")) {
            return false;
        }

        try {
            // проверка сайта на работоспособность и xml на валидность
            new SyndFeedInput().build(new XmlReader(new URL(url)));
        } catch (FeedException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Because of " + url + " throw exception: ");
        }

        return true;
    }

    public boolean isSiteUnique(NewsSite newSite) {
        return newsSiteRepository.findAll()
                .stream().noneMatch(site -> {
                    final String newUrl = newSite.getUrl().replace("/", "");
                    final String oldUrl = site.getUrl().replace("/", "");

                    boolean isOldUrlContainsNew = oldUrl.contains(newUrl);
                    boolean isNewUrlContainsOld = newUrl.contains(oldUrl);

                    return isNewUrlContainsOld || isOldUrlContainsNew;
                });
    }

    public String getRssUrl(final String sourceUrl) {
        try {
            Document sourceHtml = Jsoup.connect(sourceUrl).get();
            Elements rssLink = sourceHtml.head()
                    .select("link[type=application/rss+xml]");
            if (rssLink.size() > 0) {
                // подразумевается, что в html только один link с type=application/rss+xm
                return rssLink.get(0).attr("href");
            } else {
                throw new RuntimeException("Because of " + sourceUrl + " throw exception: ");
            }
        } catch (IOException e) {
            throw new RuntimeException("Because of " + sourceUrl + " throw exception: ");
        }
    }
}
