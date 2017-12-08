package com.tochka.newsParser.repositories;

import com.tochka.newsParser.models.NewsContent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsContentRepository extends JpaRepository<NewsContent, Long> {

    List<NewsContent> findAllByOrderByPublishDateDesc();

    List<NewsContent> findAllBySourceNewsSiteId(Long sourceNewsSiteId);

    List<NewsContent> findAllByTitleContainingIgnoreCase(String substr);
}
