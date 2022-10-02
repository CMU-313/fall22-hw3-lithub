create table T_SCORE (
    SCORE_ID_C varchar(36) not null,
    ADMIN_ID_C varchar(36)  not null,
    ADMIN_USERNAME_C varchar(50) not null,
    DOCUMENT_ID_C varchar(36) not null,
    SCORE_I int not null,
    SUBMITTED_B bit not null,
    primary key (SCORE_ID_C),
    foreign key (ADMIN_ID_C) references T_USER(USE_ID_C),
    foreign key (DOCUMENT_ID_C) references T_DOCUMENT(DOC_ID_C)
);
