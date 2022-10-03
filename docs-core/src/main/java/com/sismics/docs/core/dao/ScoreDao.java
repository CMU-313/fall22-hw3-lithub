package com.sismics.docs.core.dao;

import com.sismics.docs.core.model.jpa.Score;
import com.sismics.util.context.ThreadLocalContext;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Comment DAO.
 * 
 * @author bgamard
 */
public class ScoreDao {

    /**
     * Creates a new score.
     * 
     * @param score score
     * @return New ID
     */
    public String create(Score score) {
        // Create the UUID
        score.setId(UUID.randomUUID().toString());
        
        // Create the score
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        em.persist(score);
                
        return score.getId();
    }
    
    /**
     * Gets a score by its ID.
     * 
     * @param id Score ID
     * @return Score
     */
    public Score getById(String id) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        try {
            Query q = em.createQuery("select s from Score s where s.id = :id");
            q.setParameter("id", id);
            return (Score) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Get all scores on a document.
     * 
     * @param documentId Document ID
     * @return List of scores
     */
    public List<Score> getByDocumentId(String documentId) {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select s from Score s where s.documentId = :di");
        q.setParameter("di", documentId);
        List<?> result1 = q.getResultList();
        List<Score> result2 = new ArrayList<>();
        for (int i = 0; i < result1.size(); ++i){
            if (result1.get(i) instanceof Score){
                result2.add((Score) result1.get(i));
            }
        }
        return result2;
    }

    /**
     * Get all scores in the database.
     * 
     * @return List of scores
     */
    public List<Score> getAll() {
        EntityManager em = ThreadLocalContext.get().getEntityManager();
        Query q = em.createQuery("select s from Score s");
        List<?> result1 = q.getResultList();
        List<Score> result2 = new ArrayList<>();
        for (int i = 0; i < result1.size(); ++i){
            if (result1.get(i) instanceof Score){
                result2.add((Score) result1.get(i));
            }
        }
        return result2;
    }
}

