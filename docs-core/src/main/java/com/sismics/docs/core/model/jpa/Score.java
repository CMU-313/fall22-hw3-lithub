package com.sismics.docs.core.model.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Score entity.
 * 
 * @author bgamard
 */

@Entity
@Table(name = "T_SCORE")
public class Score{
    /**
     * Score ID.
     */
    @Id
    @Column(name = "SCO_ID_C", length = 36)
    private String id;
    
    /**
     * Document ID. The document that the score was given to
     */
    @Column(name = "SCO_IDDOC_C", length = 36, nullable = false)
    private String documentId;
    
    /**
     * User ID. The user that set the score
     */
    @Column(name = "SCO_IDUSER_C", length = 36, nullable = false)
    private String userId;
    
    /**
     * Score.
     */
    @Column(name = "SCO_VAL_I", nullable = false)
    private int score;
    
    /**
     * Set boolean: True if the score was set by the user, false if he still didn't set the score
     */
    @Column(name = "SCO_SET_B", nullable = false)
    private boolean setBit;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getSetBit(){
        return setBit;
    }
    
    public void setSetBit(boolean setBit) {
        this.setBit = setBit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
