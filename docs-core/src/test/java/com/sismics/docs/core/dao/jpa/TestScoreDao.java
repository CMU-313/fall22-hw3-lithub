package com.sismics.docs.core.dao.jpa;
import com.sismics.docs.BaseTransactionalTest;
import org.junit.Assert;
import org.junit.Test;
import com.sismics.docs.core.constant.ConfigType;
import com.sismics.docs.core.dao.*;
import com.sismics.docs.core.model.jpa.Document;
import com.sismics.docs.core.model.jpa.Score;
import com.sismics.docs.core.util.*;
import org.apache.commons.lang.StringUtils;
import java.util.*;
import com.sismics.docs.core.util.DocumentUtil;

/**
 * Tests the persistance layer.
 * 
 * @author jtremeaux
 */
public class TestScoreDao extends BaseTransactionalTest {
    @Test
    public void testScoreDao() throws Exception {

           //Create a document 
            DocumentDao daoDoc = new DocumentDao();
            Document document = new Document();
    
            document.setUserId("admin");
            document.setTitle(StringUtils.abbreviate("subject", 100));
            document.setDescription(StringUtils.abbreviate("description", 4000));
            document.setSubject(StringUtils.abbreviate("subject", 500));
            document.setFormat("EML");
            document.setSource("Inbox");
            document.setLanguage(ConfigUtil.getConfigStringValue(ConfigType.DEFAULT_LANGUAGE));
            document.setCreateDate(new Date());
        
            //Set a score to a document and set its fields 
            DocumentUtil.createDocument(document, "admin");
            String id = daoDoc.create(document, "admin");
            Score score = new Score();
            score.setDocumentId(id);
            score.setScore(20);
            score.setSetBit(false);
            score.setUserId("admin");
            ScoreDao daoScore = new ScoreDao();
            String score_id = daoScore.create(score);
            Score retrieved = daoScore.getById(score_id);
            
            //Checks whatever we stored in the database is consistent when we retrieve from database
            Assert.assertEquals(score.getScore(), retrieved.getScore());
            Assert.assertEquals(score.getSetBit(), retrieved.getSetBit());
            Assert.assertEquals(score.getDocumentId(), retrieved.getDocumentId());
            Assert.assertEquals(score.getUserId(), retrieved.getUserId());

            //Updating score and setBit to see if changes are made in database
            score.setScore(50);
            score.setSetBit(true);
            ScoreDao daoScore2 = new ScoreDao();
            String score_id2 = daoScore2.create(score);
            Score retrieved2 = daoScore2.getById(score_id2);

            //More Tests after the Update 
            Assert.assertEquals(score.getScore(), retrieved2.getScore());
            Assert.assertEquals(score.getSetBit(), retrieved2.getSetBit());

            //Checking if another admin can store a score into database for same document 
            String id2 = daoDoc.create(document, "admin2");
            Score score2 = new Score();
            score2.setDocumentId(id2);
            score2.setScore(65);
            score2.setSetBit(false);
            score2.setUserId("admin2");
        
    }
}
