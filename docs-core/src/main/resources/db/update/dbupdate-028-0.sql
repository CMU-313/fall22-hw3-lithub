alter table T_DOCUMENT add column DOC_SCORE_C varchar(500);
update T_CONFIG set CFG_VALUE_C = '28' where CFG_ID_C = 'DB_VERSION';