CREATE TABLE document_score_history (
    score_change_id int  NOT NULL,
    admin_id int  NOT NULL,
    admin_name varchar(500)  NOT NULL,
    document_id int  NOT NULL,
    "user" varchar(500)  NOT NULL,
    score int   NOT NULL,
    CONSTRAINT document_score_history_pk PRIMARY KEY (score_change_id)
);
