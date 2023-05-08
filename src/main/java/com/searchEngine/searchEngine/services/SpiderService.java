package com.searchEngine.searchEngine.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.searchEngine.searchEngine.entities.WebPage;
import com.searchEngine.searchEngine.repositories.WebPageRepository;

import java.io.InputStreamReader;
import static org.hibernate.internal.util.StringHelper.isBlank;

import java.io.BufferedReader;

@Service
public class SpiderService {

    @Autowired
    WebPageRepository webPageRepository;
 
    public void indexWebPages(){
        List<WebPage> webPagesToIndex = webPageRepository.getPagesToIndex();
        webPagesToIndex.parallelStream().forEach(webPage -> {
            try {
                index(webPage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void index(WebPage webPage) throws IOException {
        //Set titlle and description
        String content = dowloadWeb(webPage.getUrl());
        if(isBlank(content)) return;
        String tittle = getTitle(content);
        String description = getDescription(content);
        webPage.setDescription(description);
        webPage.setTitle(tittle);
        webPageRepository.save(webPage);
        String domain = getDomain(webPage.getUrl());

        //index web pages
        List<String> links = extractLinks(content,domain);
        links.stream()
            .filter(link -> link.length() < 255)
            .forEach(link ->{
                if(!webPageRepository.exist(link)){
                    WebPage web = new WebPage();
                    web.setUrl(link);
                    webPageRepository.save(web);
                }
        });
        

    }

        private String getDomain(String url) {
        String[] aux = url.split("/");
        return aux[0] + "//" + aux[2];
    }

    

    private List<String> extractLinks(String content, String domain) {
        List<String> links = new ArrayList<>();
        String[] href = content.split("href=\"");
        for(int i = 1; i < href.length -1; i = i+2){
            String link = href[i].split("\"")[0];
            links.add(link);
        }
        return filterLinks(links,domain);
    }

    private List<String> filterLinks(List<String> links, String domain){
        String excludeExtension[] = new String[]{"json","jpg","png","css","js","woff2"} ;
        
        List<String> linksFiltered = links.stream()
                .filter(link -> Arrays.stream(excludeExtension).noneMatch(link::endsWith))
                .filter(link -> link.startsWith("http"))
                .map(link -> link.startsWith("/") ? domain + link : link)
                .collect(Collectors.toList());
        return linksFiltered;
    }

    private String dowloadWeb(String link){
        try{
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //conecta a la url
            String encoding = conn.getContentEncoding(); // trae la cabecera de laweb para conocer como esta encodeado

            InputStream input = conn.getInputStream(); // trae el contenido de la web
            String result = new BufferedReader(new InputStreamReader(input))
                .lines().collect(Collectors.joining()); // lines: pasa todo el contenido a lineas, collect joining: junta toda la informacion
    
            return result;
        }catch(IOException e){

        }
        return "";
    }

    private String getTitle(String content){
        String cont[] = content.split("<title>");
        String title =  cont.length > 1 ? cont[1].split("</")[0] : "No title";
        return title.length() <= 70 ? title : title.substring(0,70) + "...";
    }

    private String getDescription(String content){
        String cont[] = content.split("<meta name=\"description\" content=\"");
        if(cont.length == 1){
            cont = content.split("<meta name=\"DESCRIPTION\" content=\"");
        }
        if(cont.length == 1) return "No description";
        String cont2 = cont[1].split("/>")[0];
        int length = cont2.length();
        return length <= 149 ? cont2 : cont2.substring(0,149) + "...";
    }
}
