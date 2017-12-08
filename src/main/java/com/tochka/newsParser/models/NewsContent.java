package com.tochka.newsParser.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Date;

@Entity
public class NewsContent {

    @Id
    @GeneratedValue
    private Long id;

    private String uri;

    @Column(length = 1000)
    private String title;

    @Column(length = 10000)
    private String description;

    private Date publishDate;

    @Column(length = 1000)
    private String link;

    private String author;

    private Long sourceNewsSiteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getSourceNewsSiteId() {
        return sourceNewsSiteId;
    }

    public void setSourceNewsSiteId(Long sourceNewsSiteId) {
        this.sourceNewsSiteId = sourceNewsSiteId;
    }

    @Override
    public String toString() {
        return "NewsContent{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishDate=" + publishDate +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", sourceNewsSiteId=" + sourceNewsSiteId +
                '}';
    }
}
