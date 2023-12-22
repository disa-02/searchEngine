package com.searchEngine.searchEngine;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.searchEngine.searchEngine.entities.WebPage;
import com.searchEngine.searchEngine.repositories.WebPageRepository;

@SpringBootApplication
public class SearchEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchEngineApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(WebPageRepository webPageRepository){
		return args ->{
			WebPage webPage = new WebPage();
			webPage.setUrl("https://www.lanacion.com.ar/");
			webPageRepository.save(webPage);
		};
	}
}
