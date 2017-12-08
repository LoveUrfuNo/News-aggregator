package com.tochka.newsParser.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NewsSite {

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    private boolean link;

    private boolean author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url.trim();
    }

    public boolean isLink() {
        return link;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public boolean isAuthor() {
        return author;
    }

    public void setAuthor(boolean author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "NewsSite{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", link=" + link +
                ", author=" + author +
                '}';
    }
}
