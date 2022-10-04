create memory table T_SCORE (SCO_ID_C varchar(36) not null, SCO_IDUSER_C varchar(36) not null, SCO_IDDOC_C varchar(36) not null, SCO_VAL_I int not null, SCO_SET_B bit not null, primary key (SCO_ID_C));

alter table T_SCORE add constraint FK_SCO_IDUSER_C foreign key (SCO_IDUSER_C) references T_USER (USE_ID_C) on delete restrict on update restrict;
alter table T_SCORE add constraint FK_SCO_IDDOC_C foreign key (SCO_IDDOC_C) references T_DOCUMENT (DOC_ID_C) on delete restrict on update restrict;

update T_CONFIG set CFG_VALUE_C = '28' where CFG_ID_C = 'DB_VERSION';