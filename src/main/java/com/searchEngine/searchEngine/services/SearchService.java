package com.searchEngine.searchEngine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.searchEngine.searchEngine.entities.WebPage;
import com.searchEngine.searchEngine.repositories.WebPageRepository;


@Service
public class SearchService {

    @Autowired
    WebPageRepository webPageRepository;

   


    public List<WebPage> search(String[] text){
        return webPageRepository.search(text);

    }


    
}
