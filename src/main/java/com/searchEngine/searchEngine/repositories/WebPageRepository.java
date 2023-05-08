package com.searchEngine.searchEngine.repositories;
import java.util.List;
import com.searchEngine.searchEngine.entities.WebPage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;

@Repository
public class WebPageRepository  {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public List<WebPage> search(String[] search){
        String queryString = "FROM WebPage WHERE  ";
        for(int i = 0; i < search.length; i++){
            queryString += "description like :textSearch" + i;
            if(i < search.length -1){
                queryString += " AND ";
            }
        }
        TypedQuery<WebPage> query = entityManager.createQuery(queryString,WebPage.class);
        for (int i = 0 ; i < search.length; i++){
            query.setParameter("textSearch" + i, "%" + search[i] + "%");
        }

        return query.getResultList();
    }

    public List<WebPage> getPagesToIndex() {
        String query = "FROM WebPage WHERE title is null AND description is null";
        return entityManager.createQuery(query).setMaxResults(200).getResultList();
    }

    @Transactional
    public void save(WebPage webPage) {
        entityManager.merge(webPage);
    }

    public Boolean exist(String link){
        String query = "FROM WebPage WHERE url = :url";
        List<WebPage> pages = entityManager.createQuery(query)
            .setParameter("url", link).getResultList();
        return pages.size() != 0;
    }
    
}
