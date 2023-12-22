package com.searchEngine.searchEngine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.searchEngine.searchEngine.services.SearchService;
import java.util.List;
import com.searchEngine.searchEngine.entities.WebPage;
import com.searchEngine.searchEngine.services.SpiderService;


@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    SpiderService spiderService;

    @GetMapping("api/search")
    public ResponseEntity<?> search(@RequestParam String text){
        String textSearch[] = text.split(" ");
        List<WebPage> result = searchService.search(textSearch);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/spider")
    public void testSpider(){
        spiderService.indexWebPages();
    }


    
}