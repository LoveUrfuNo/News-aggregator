package com.tochka.newsParser.controllers;

import com.tochka.newsParser.dto.SearchParameters;
import com.tochka.newsParser.models.NewsSite;
import com.tochka.newsParser.repositories.NewsContentRepository;
import com.tochka.newsParser.repositories.NewsSiteRepository;
import com.tochka.newsParser.services.NewsSiteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(NewsSiteService.class);

    @Autowired
    private NewsSiteRepository newsSiteRepository;

    @Autowired
    private NewsSiteService newsSiteService;

    @Autowired
    private NewsContentRepository newsContentRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String start(Model model) {

        model.addAttribute("newsSite", new NewsSite());

        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addNewsSite(@ModelAttribute NewsSite newsSite, Model model) {

        final String url = newsSite.getUrl();

        boolean isSiteValid = false;
        boolean isSiteUnique = false;
        try {
            isSiteValid = newsSiteService.isSiteValid(newsSite);

            if (!isSiteValid) {
                final String correctRssUrl = newsSiteService.getRssUrl(url);
                newsSite.setUrl(correctRssUrl);

                isSiteValid = true;
            }

            isSiteUnique = newsSiteService.isSiteUnique(newsSite);
        } catch (RuntimeException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }

        final String addingTitle = "addingResult";
        final String addingResult = !isSiteValid ? "validation failed" :
                !isSiteUnique ? "this site already added" : "added";

        if (addingResult.equals("added")) {
            newsSiteRepository.save(newsSite);
        }

        model.addAttribute(addingTitle, addingResult);

        return "index";
    }

    @RequestMapping(value = "/newsSites", method = RequestMethod.GET)
    public String getNewsSites(Model model) {

        model.addAttribute("newsSites", newsSiteRepository.findAll());

        return "newsSites";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteNewsSite(@PathVariable Long id, Model model) {

        newsSiteRepository.delete(id);

        model.addAttribute("deletingResult", "deleted");
        model.addAttribute("newsSite", new NewsSite());

        return "index";
    }

    @RequestMapping(value = "/myNews", method = RequestMethod.GET)
    public String showNews(Model model) {

        model.addAttribute("news", newsContentRepository
                .findAllByOrderByPublishDateDesc());
        model.addAttribute("searchParameters", new SearchParameters());

        return "news";
    }

    @RequestMapping(value = "/newsSearch", method = RequestMethod.POST)
    public String newsSearch(@ModelAttribute SearchParameters searchParameters,
                             Model model) {

        model.addAttribute("news", newsContentRepository
                .findAllByTitleContainingIgnoreCase(searchParameters.getSearchLine()));
        model.addAttribute("searchParameters", new SearchParameters());

        return "news";
    }
}
