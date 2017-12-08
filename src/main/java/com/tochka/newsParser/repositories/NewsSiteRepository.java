package com.tochka.newsParser.repositories;

import com.tochka.newsParser.models.NewsSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsSiteRepository extends JpaRepository<NewsSite, Long> {
}
